package controllers

import javax.inject.Inject
import models.Land
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.LandService

import scala.concurrent.ExecutionContext

class LandControllerImpl @Inject()(val controllerComponents: ControllerComponents, val landService: LandService) extends LandController

trait LandController extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext
  val landService: LandService

  val getAllLands: String => Action[AnyContent] = planeId => Action.async { implicit request =>
    landService.retrieveLands(planeId).map {
      case lands@_ :: _ => Ok(Json.toJson(lands))
      case Nil => NoContent
    }
  }

  val getSingleLand: String => Action[AnyContent] = landId => Action.async { implicit request =>
    landService.retrieveSingleLand(landId).map {
      case Some(land) => Ok(Json.toJson(land))
      case None => NotFound
    }
  }

  val create: Action[Land] = Action.async(parse.json[Land]) { implicit request =>
    landService.createLand(request.body).map(land => Created(Json.toJson(land)))
  }

  val update: Action[Land] = Action.async(parse.json[Land]) { implicit request =>
    landService.updateLand(request.body).map {
      case Some(land) => Ok(Json.toJson(land))
      case None => NotFound
    }
  }

  val remove: String => Action[AnyContent] = landId => Action.async { implicit request =>
    landService.removeLand(landId).map {
      case Some(land) => Ok(Json.toJson(land))
      case None => NotFound
    }
  }

}
