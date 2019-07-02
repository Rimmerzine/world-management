package repositories

import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.play.json.ImplicitBSONHandlers._
import reactivemongo.play.json.JSONSerializationPack.{Reader, Writer}
import reactivemongo.play.json.collection.JSONBatchCommands.FindAndModifyCommand.FindAndModifyResult
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}

trait BaseRepository {

  val reactiveApi: ReactiveMongoApi
  val databaseName: String
  val collectionName: String

  private def collection(implicit ec: ExecutionContext): Future[JSONCollection] = {
    reactiveApi.connection.database(databaseName).map(_.collection[JSONCollection](collectionName))
  }

  protected def findOne[A](selector: JsObject, projection: Option[JsObject] = Some(Json.obj("_id" -> 0)))
                          (implicit reads: Reader[A], ec: ExecutionContext): Future[Option[A]] = {
    collection.flatMap(_.find(selector, projection).one[A])
  }

  protected def find[A](selector: JsObject, projection: Option[JsObject] = Some(Json.obj("_id" -> 0)))
                       (implicit reads: Reader[A], ec: ExecutionContext): Future[List[A]] = {
    collection.flatMap(_.find(selector, projection).cursor[A](ReadPreference.primaryPreferred).collect[List](-1, Cursor.FailOnError[List[A]]()))
  }

  protected def insert[A](data: A)(implicit writes: Writer[A], ec: ExecutionContext): Future[A] = {
    collection.flatMap(_.insert.one(data)).map(_ => data)
  }

  protected def findAndUpdate[A](selector: JsObject, update: JsObject)
                                (implicit writes: Writer[A], reader: Reader[A], ec: ExecutionContext): Future[Option[A]] = {
    collection.flatMap[FindAndModifyResult](_.findAndUpdate(selector, update, fetchNewObject = true)).map(_.result[A])
  }

}
