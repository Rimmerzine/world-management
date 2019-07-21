package repositories

import javax.inject.Inject
import models.Land
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}

class LandRepositoryImpl @Inject()(val reactiveApi: ReactiveMongoApi) extends LandRepository {
  val databaseName: String = "world-management"
  val collectionName: String = "lands"
}

trait LandRepository extends BaseRepository {

  def retrieveLands(planeId: String)(implicit ec: ExecutionContext): Future[List[Land]] = {
    val selector: JsObject = Json.obj("planeId" -> planeId)
    find[Land](selector)
  }

  def retrieveSingleLand(landId: String)(implicit ec: ExecutionContext): Future[Option[Land]] = {
    val selector: JsObject = Json.obj("landId" -> landId)
    findOne[Land](selector)
  }

  def insertLand(land: Land)(implicit ec: ExecutionContext): Future[Land] = {
    insert(land)
  }

  def updateLand(land: Land)(implicit ec: ExecutionContext): Future[Option[Land]] = {
    val selector: JsObject = Json.obj("landId" -> land.landId)
    findAndUpdate[Land](selector, Json.toJsObject(land))
  }

  def removeLand(landId: String)(implicit ec: ExecutionContext): Future[Option[Land]] = {
    val selector: JsObject = Json.obj("landId" -> landId)
    findAndRemove[Land](selector)
  }

}
