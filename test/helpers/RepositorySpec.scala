package helpers

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.{MultiBulkWriteResult, WriteResult}
import reactivemongo.api.{Cursor, DefaultDB, ReadPreference}
import reactivemongo.play.json.ImplicitBSONHandlers.JsObjectDocumentWriter
import reactivemongo.play.json.JSONSerializationPack.{Reader, Writer}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.ExecutionContext.Implicits.global

trait RepositorySpec extends UnitSpec with GuiceOneAppPerSuite with BeforeAndAfterAll with BeforeAndAfterEach {

  val testReactiveApi: ReactiveMongoApi = app.injector.instanceOf[ReactiveMongoApi]
  val testDatabaseName: String = "world-management"
  val testCollectionName: String

  lazy val database: DefaultDB = await(testReactiveApi.connection.database(testDatabaseName))
  lazy val collection: JSONCollection = database.collection[JSONCollection](testCollectionName)

  override def beforeEach(): Unit = {
    await(collection.drop(failIfNotFound = false))
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    await(collection.drop(failIfNotFound = false))
    super.afterEach()
  }

  def insertOne[A](data: A)(implicit writes: Writer[A]): WriteResult = await(collection.insert(false).one(data))

  def insertMany[A](data: List[A])(implicit writes: Writer[A]): MultiBulkWriteResult = await(collection.insert(false).many(data))

  def findAll[A](implicit reads: Reader[A]): List[A] = {
    await(collection.find(Json.obj(), None).cursor[A](ReadPreference.primaryPreferred).collect[List](-1, Cursor.FailOnError[List[A]]()))
  }

}
