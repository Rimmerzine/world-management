package controllers

import javax.inject.Inject
import models.Plane
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.PlaneService

import scala.concurrent.ExecutionContext

class PlaneControllerImpl @Inject()(val controllerComponents: ControllerComponents, val planeService: PlaneService) extends PlaneController

trait PlaneController extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext
  val planeService: PlaneService

  val getAllPlanes: String => Action[AnyContent] = campaignId => Action.async { implicit request =>
    planeService.retrievePlanes(campaignId).map {
      case planes@_ :: _ => Ok(Json.toJson(planes))
      case Nil => NoContent
    }
  }

  val getSinglePlane: String => Action[AnyContent] = planeId => Action.async { implicit request =>
    planeService.retrieveSinglePlane(planeId).map {
      case Some(plane) => Ok(Json.toJson(plane))
      case None => NotFound
    }
  }

  val create: Action[Plane] = Action.async(parse.json[Plane]) { implicit request =>
    planeService.createPlane(request.body).map(plane => Created(Json.toJson(plane)))
  }

  val update: Action[Plane] = Action.async(parse.json[Plane]) { implicit request =>
    planeService.updatePlane(request.body).map {
      case Some(plane) => Ok(Json.toJson(plane))
      case None => NotFound
    }
  }

  val remove: String => Action[AnyContent] = planeId => Action.async { implicit request =>
    planeService.removePlane(planeId).map {
      case Some(plane) => Ok(Json.toJson(plane))
      case None => NotFound
    }
  }

}
