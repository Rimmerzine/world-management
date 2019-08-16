package services

import helpers.UnitSpec
import models.WorldElement
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import repositories.WorldElementRepository
import utils.WorldElementConstants

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class WorldElementServiceSpec extends UnitSpec with WorldElementConstants {

  class Setup {
    val mockWorldElementRepository: WorldElementRepository = mock[WorldElementRepository]

    val service: WorldElementService = new WorldElementService {
      val worldElementRepository: WorldElementRepository = mockWorldElementRepository
    }
  }

  "retrieveCampaigns" must {
    "return back the campaigns from the repository" in new Setup {
      val campaignsList: List[WorldElement] = List(campaign, campaignMinimal)
      when(mockWorldElementRepository.retrieveWorldElements(any())) thenReturn Future.successful(campaignsList)
      await(service.retrieveWorldElements) mustBe campaignsList
    }
  }

  "retrieveSingleCampaign" must {
    "return back the campaign from the repository" in new Setup {
      when(mockWorldElementRepository.retrieveSingleWorldElement(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))
      await(service.retrieveSingleWorldElement(campaign.id)) mustBe Some(campaign)
    }
  }

  "createCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockWorldElementRepository.insertWorldElement(matches(campaign))(any())) thenReturn Future.successful(campaign)
      await(service.createWorldElement(campaign)) mustBe campaign
    }
  }

  "updateCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockWorldElementRepository.updateWorldElement(matches(campaign))(any())) thenReturn Future.successful(Some(campaign))
      await(service.updateWorldElement(campaign)) mustBe Some(campaign)
    }
  }

  "removeCampaign" must {
    "return what is returned from the repository" in new Setup {
      when(mockWorldElementRepository.removeWorldElement(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))
      await(service.removeWorldElement(campaign.id)) mustBe Some(campaign)
    }
  }

}
