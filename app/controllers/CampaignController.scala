package controllers

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.CampaignService

import scala.concurrent.ExecutionContext

class CampaignControllerImpl @Inject()(val controllerComponents: ControllerComponents, val campaignService: CampaignService) extends CampaignController

trait CampaignController extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext
  val campaignService: CampaignService

  val campaigns: Action[AnyContent] = Action.async { implicit request =>
    campaignService.campaigns.map {
      case campaigns@_ :: _ => Ok(Json.toJson(campaigns))
      case Nil => NoContent
    }
  }

}
