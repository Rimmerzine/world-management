package controllers

import javax.inject.Inject
import models.Creature
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.CreatureService

import scala.concurrent.ExecutionContext

class CreatureControllerImpl @Inject()(val controllerComponents: ControllerComponents, val creatureService: CreatureService) extends CreatureController

trait CreatureController extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext
  val creatureService: CreatureService

  def getAllCreatures(challengeRating: Option[Double], nameStart: Option[Char]) : Action[AnyContent] = Action.async { implicit request =>
    creatureService.retrieveCreatures(challengeRating, nameStart).map {
      case creatures@_ :: _ => Ok(Json.toJson(creatures))
      case Nil => NoContent
    }
  }

  val getSingleCreature: String => Action[AnyContent] = creatureId => Action.async { implicit request =>
    creatureService.retrieveSingleCreature(creatureId).map {
      case Some(creature) => Ok(Json.toJson(creature))
      case None => NotFound
    }
  }

  val create: Action[Creature] = Action.async(parse.json[Creature]) { implicit request =>
    creatureService.createCreature(request.body).map(creature => Created(Json.toJson(creature)))
  }

  val update: Action[Creature] = Action.async(parse.json[Creature]) { implicit request =>
    creatureService.updateCreature(request.body).map {
      case Some(creature) => Ok(Json.toJson(creature))
      case None => NotFound
    }
  }

  val remove: String => Action[AnyContent] = creatureId => Action.async { implicit request =>
    creatureService.removeCreature(creatureId).map {
      case Some(creature) => Ok(Json.toJson(creature))
      case None => NotFound
    }
  }

}
