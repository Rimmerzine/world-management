package repositories

import javax.inject.Inject
import models.Campaign
import play.api.libs.json.{JsObject, Json}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}

class CampaignRepositoryImpl @Inject()(val reactiveApi: ReactiveMongoApi) extends CampaignRepository {
  val databaseName: String = "world-management"
  val collectionName: String = "campaigns"
}

trait CampaignRepository extends BaseRepository {

  def retrieveCampaigns(implicit ec: ExecutionContext): Future[List[Campaign]] = {
    val selector: JsObject = Json.obj()
    find[Campaign](selector)
  }

  def insertCampaign(campaign: Campaign)(implicit ec: ExecutionContext): Future[Campaign] = {
    insert(campaign)
  }

  def updateCampaign(campaign: Campaign)(implicit ec: ExecutionContext): Future[Option[Campaign]] = {
    val selector: JsObject = Json.obj("id" -> campaign.id)
    findAndUpdate[Campaign](selector, Json.toJsObject(campaign))
  }

  def removeCampaign(campaignId: String)(implicit ec: ExecutionContext): Future[Option[Campaign]] = {
    val selector: JsObject = Json.obj("id" -> campaignId)
    findAndRemove[Campaign](selector)
  }

}
