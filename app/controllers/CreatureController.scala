package controllers

import javax.inject.Inject
import models.Creature
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.CreatureService

import scala.concurrent.ExecutionContext

class CreatureControllerImpl @Inject()(val controllerComponents: ControllerComponents,
                                       val creatureService: CreatureService) extends CreatureController

trait CreatureController extends BaseController {

  val creatureService: CreatureService

  implicit val ec: ExecutionContext = controllerComponents.executionContext

  def getAllCreatures: Action[AnyContent] = Action.async { implicit request =>
    creatureService.retrieveCreatures.map {
      case creatures if creatures.nonEmpty => Ok(Json.toJson(creatures))
      case _ => NoContent
    }
  }

  def getSingleCreature(creatureId: String): Action[AnyContent] = Action.async { implicit request =>
    creatureService.retrieveSingleCreature(creatureId).map {
      case Some(creature) => Ok(Json.toJson(creature))
      case None => NotFound
    }
  }

  def create: Action[Creature] = Action.async(parse.json[Creature]) { implicit request =>
    creatureService.createCreature(request.body).map(creature => Created(Json.toJson(creature)))
  }

  def update: Action[Creature] = Action.async(parse.json[Creature]) { implicit request =>
    creatureService.updateCreature(request.body).map {
      case Some(creature) => Ok(Json.toJson(creature))
      case None => NotFound
    }
  }

  def remove: String => Action[AnyContent] = creatureId => Action.async { implicit request =>
    creatureService.removeCreature(creatureId).map {
      case Some(creature) => Ok(Json.toJson(creature))
      case None => NotFound
    }
  }

}
