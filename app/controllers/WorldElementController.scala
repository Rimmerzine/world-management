package controllers

import javax.inject.Inject
import models.WorldElement
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.WorldElementService

import scala.concurrent.ExecutionContext

class WorldElementControllerImpl @Inject()(
                                            val controllerComponents: ControllerComponents,
                                            val worldElementService: WorldElementService
                                          ) extends WorldElementController

trait WorldElementController extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext
  val worldElementService: WorldElementService

  val getAllWorldElements: Action[AnyContent] = Action.async { implicit request =>
    worldElementService.retrieveWorldElements.map {
      case worldElements@_ :: _ => Ok(Json.toJson(worldElements))
      case Nil => NoContent
    }
  }

  val getSingleWorldElement: String => Action[AnyContent] = worldElementId => Action.async { implicit request =>
    worldElementService.retrieveSingleWorldElement(worldElementId).map {
      case Some(worldElement) => Ok(Json.toJson(worldElement))
      case None => NotFound
    }
  }

  val create: Action[WorldElement] = Action.async(parse.json[WorldElement]) { implicit request =>
    worldElementService.createWorldElement(request.body).map(worldElement => Created(Json.toJson(worldElement)))
  }

  val update: Action[WorldElement] = Action.async(parse.json[WorldElement]) { implicit request =>
    worldElementService.updateWorldElement(request.body).map {
      case Some(worldElement) => Ok(Json.toJson(worldElement))
      case None => NotFound
    }
  }

  val remove: String => Action[AnyContent] = worldElementId => Action.async { implicit request =>
    worldElementService.removeWorldElement(worldElementId).map {
      case Some(worldElement) => Ok(Json.toJson(worldElement))
      case None => NotFound
    }
  }

}
