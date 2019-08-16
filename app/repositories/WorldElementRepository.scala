package repositories

import config.AppConfig
import javax.inject.Inject
import models.WorldElement
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}

class WorldElementRepositoryImpl @Inject()(val reactiveApi: ReactiveMongoApi, val appConfig: AppConfig) extends WorldElementRepository {
  val collectionName: String = "world-elements"
}

trait WorldElementRepository extends BaseRepository {

  def retrieveWorldElements(implicit ec: ExecutionContext): Future[List[WorldElement]] = {
    val selector: JsObject = Json.obj()
    find[WorldElement](selector)
  }

  def retrieveSingleWorldElement(worldElementId: String)(implicit ec: ExecutionContext): Future[Option[WorldElement]] = {
    val selector: JsObject = Json.obj("id" -> worldElementId)
    findOne[WorldElement](selector)
  }

  def insertWorldElement(worldElement: WorldElement)(implicit ec: ExecutionContext): Future[WorldElement] = {
    insert(worldElement)
  }

  def updateWorldElement(worldElement: WorldElement)(implicit ec: ExecutionContext): Future[Option[WorldElement]] = {
    val selector: JsObject = Json.obj("id" -> worldElement.id)
    findAndUpdate[WorldElement](selector, Json.toJsObject(worldElement))
  }

  def removeWorldElement(worldElementId: String)(implicit ec: ExecutionContext): Future[Option[WorldElement]] = {
    val selector: JsObject = Json.obj("id" -> worldElementId)
    findAndRemove[WorldElement](selector)
  }

}
