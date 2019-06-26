package services

import helpers.UnitSpec
import models.Campaign
import repositories.CampaignRepository
import org.mockito.Mockito.when
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CampaignServiceSpec extends UnitSpec with TestConstants {

  class Setup extends CampaignService {
    val campaignRepository: CampaignRepository = mock[CampaignRepository]
  }

  "campaigns" must {
    "return back the campaigns from the repository" in new Setup {
      val campaignsList: List[Campaign] = testCampaigns(5)
      when(campaignRepository.retrieveCampaigns) thenReturn Future.successful(campaignsList)
      await(campaigns) mustBe campaignsList
    }
  }

}
