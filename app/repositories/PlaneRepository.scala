package repositories

import javax.inject.Inject
import models.Plane
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}

class PlaneRepositoryImpl @Inject()(val reactiveApi: ReactiveMongoApi) extends PlaneRepository {
  val databaseName: String = "world-management"
  val collectionName: String = "planes"
}

trait PlaneRepository extends BaseRepository {

  def retrievePlanes(campaignId: String)(implicit ec: ExecutionContext): Future[List[Plane]] = {
    val selector: JsObject = Json.obj("campaignId" -> campaignId)
    find[Plane](selector)
  }

  def retrieveSinglePlane(planeId: String)(implicit ec: ExecutionContext): Future[Option[Plane]] = {
    val selector: JsObject = Json.obj("planeId" -> planeId)
    findOne[Plane](selector)
  }

  def insertPlane(plane: Plane)(implicit ec: ExecutionContext): Future[Plane] = {
    insert(plane)
  }

  def updatePlane(plane: Plane)(implicit ec: ExecutionContext): Future[Option[Plane]] = {
    val selector: JsObject = Json.obj("planeId" -> plane.planeId)
    findAndUpdate[Plane](selector, Json.toJsObject(plane))
  }

  def removePlane(planeId: String)(implicit ec: ExecutionContext): Future[Option[Plane]] = {
    val selector: JsObject = Json.obj("planeId" -> planeId)
    findAndRemove[Plane](selector)
  }

}
