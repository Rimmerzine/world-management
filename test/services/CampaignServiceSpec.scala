package services

import helpers.UnitSpec
import models.Campaign
import repositories.CampaignRepository
import org.mockito.Mockito.when
import utils.TestConstants
import org.mockito.ArgumentMatchers.{eq => matches, any}

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
      val campaignsList: List[Campaign] = testCampaigns(5)
      when(mockCampaignRepository.retrieveCampaigns(any())) thenReturn Future.successful(campaignsList)
      await(service.retrieveCampaigns) mustBe campaignsList
    }
  }

  "retrieveSingleCampaign" must {
    "return back the campaign from the repository" in new Setup {
      when(mockCampaignRepository.retrieveSingleCampaign(matches(testCampaign.id))(any())) thenReturn Future.successful(Some(testCampaign))
      await(service.retrieveSingleCampaign(testCampaign.id)) mustBe Some(testCampaign)
    }
  }

  "createCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockCampaignRepository.insertCampaign(matches(testCampaign))(any())) thenReturn Future.successful(testCampaign)
      await(service.createCampaign(testCampaign)) mustBe testCampaign
    }
  }

  "updateCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockCampaignRepository.updateCampaign(matches(testCampaign))(any())) thenReturn Future.successful(Some(testCampaign))
      await(service.updateCampaign(testCampaign)) mustBe Some(testCampaign)
    }
  }

  "removeCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockCampaignRepository.removeCampaign(matches(testCampaign.id))(any())) thenReturn Future.successful(Some(testCampaign))
      await(service.removeCampaign(testCampaign.id)) mustBe Some(testCampaign)
    }
  }

}
