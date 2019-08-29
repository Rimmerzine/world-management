package services

import helpers.UnitSpec
import models.Campaign
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import repositories.CampaignRepository
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CampaignServiceSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockCampaignRepository: CampaignRepository = mock[CampaignRepository]

    val service: CampaignService = new CampaignService {
      val campaignRepository: CampaignRepository = mockCampaignRepository
    }
  }

  "retrieveCampaigns" must {
    "return back the campaigns from the repository" in new Setup {
      val campaignsList: List[Campaign] = List(campaign, campaignMinimal)
      when(mockCampaignRepository.retrieveCampaigns(any())) thenReturn Future.successful(campaignsList)
      await(service.retrieveCampaigns) mustBe campaignsList
    }
  }

  "retrieveSingleCampaign" must {
    "return back the campaign from the repository" in new Setup {
      when(mockCampaignRepository.retrieveSingleCampaign(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))
      await(service.retrieveSingleCampaign(campaign.id)) mustBe Some(campaign)
    }
  }

  "createCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockCampaignRepository.insertCampaign(matches(campaign))(any())) thenReturn Future.successful(campaign)
      await(service.createCampaign(campaign)) mustBe campaign
    }
  }

  "updateCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockCampaignRepository.updateCampaign(matches(campaign))(any())) thenReturn Future.successful(Some(campaign))
      await(service.updateCampaign(campaign)) mustBe Some(campaign)
    }
  }

  "removeCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockCampaignRepository.removeCampaign(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))
      await(service.removeCampaign(campaign.id)) mustBe Some(campaign)
    }
  }

}
