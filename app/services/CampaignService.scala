package services

import javax.inject.Inject
import models.Campaign
import repositories.CampaignRepository

import scala.concurrent.{ExecutionContext, Future}

class CampaignServiceImpl @Inject()(val campaignRepository: CampaignRepository) extends CampaignService

trait CampaignService {

  protected val campaignRepository: CampaignRepository

  def campaigns(implicit ec: ExecutionContext): Future[List[Campaign]] = campaignRepository.retrieveCampaigns

  def create(campaign: Campaign)(implicit ec: ExecutionContext): Future[Campaign] = campaignRepository.insertCampaign(campaign)

}
