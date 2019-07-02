package services

import javax.inject.Inject
import models.Campaign
import repositories.CampaignRepository

import scala.concurrent.{ExecutionContext, Future}

class CampaignServiceImpl @Inject()(val campaignRepository: CampaignRepository) extends CampaignService

trait CampaignService {

  protected val campaignRepository: CampaignRepository

  def retrieveCampaigns(implicit ec: ExecutionContext): Future[List[Campaign]] = campaignRepository.retrieveCampaigns

  def createCampaign(campaign: Campaign)(implicit ec: ExecutionContext): Future[Campaign] = campaignRepository.insertCampaign(campaign)

  def updateCampaign(campaign: Campaign)(implicit ec: ExecutionContext): Future[Option[Campaign]] = campaignRepository.updateCampaign(campaign)

  def removeCampaign(campaignId: String)(implicit ec: ExecutionContext): Future[Option[Campaign]] = campaignRepository.removeCampaign(campaignId)

}
