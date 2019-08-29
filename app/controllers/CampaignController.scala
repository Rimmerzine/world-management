package controllers

import javax.inject.Inject
import models.Campaign
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.CampaignService

import scala.concurrent.ExecutionContext

class CampaignControllerImpl @Inject()(val controllerComponents: ControllerComponents, val campaignService: CampaignService) extends CampaignController

trait CampaignController extends BaseController {

  implicit val ec: ExecutionContext = controllerComponents.executionContext
  val campaignService: CampaignService

  val getAllCampaigns: Action[AnyContent] = Action.async { implicit request =>
    campaignService.retrieveCampaigns.map {
      case campaigns@_ :: _ => Ok(Json.toJson(campaigns))
      case Nil => NoContent
    }
  }

  val getSingleCampaign: String => Action[AnyContent] = campaignId => Action.async { implicit request =>
    campaignService.retrieveSingleCampaign(campaignId).map {
      case Some(campaign) => Ok(Json.toJson(campaign))
      case None => NotFound
    }
  }

  val create: Action[Campaign] = Action.async(parse.json[Campaign](Campaign.reads)) { implicit request =>
    campaignService.createCampaign(request.body).map(campaign => Created(Json.toJson(campaign)))
  }

  val update: Action[Campaign] = Action.async(parse.json[Campaign](Campaign.reads)) { implicit request =>
    campaignService.updateCampaign(request.body).map {
      case Some(campaign) => Ok(Json.toJson(campaign))
      case None => NotFound
    }
  }

  val remove: String => Action[AnyContent] = campaignId => Action.async { implicit request =>
    campaignService.removeCampaign(campaignId).map {
      case Some(campaign) => Ok(Json.toJson(campaign))
      case None => NotFound
    }
  }

}
