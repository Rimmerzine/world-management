package repositories

import config.AppConfig
import helpers.RepositorySpec
import models.Campaign
import play.modules.reactivemongo.ReactiveMongoApi
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class CampaignRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-campaigns"

  class Setup(repoCampaigns: Campaign*) {
    val repository: CampaignRepository = new CampaignRepository {
      val appConfig: AppConfig = testAppConfig
      val reactiveApi: ReactiveMongoApi = testReactiveApi
      val collectionName: String = testCollectionName
    }

    insertMany(repoCampaigns.toList)
  }

  "retrieveCampaigns" must {
    "return campaigns from the database" when {
      "there is one campaign in the database" in new Setup(campaign) {
        await(repository.retrieveCampaigns) mustBe List(campaign)
      }
      "there are multiple campaigns in the database" in new Setup(campaign, campaignMinimal) {
        await(repository.retrieveCampaigns) mustBe List(campaign, campaignMinimal)
      }
    }
    "return nothing from the database" when {
      "there are no campaigns stored in the database" in new Setup {
        await(repository.retrieveCampaigns) mustBe List.empty[Campaign]
      }
    }
  }

  "retrieveSingleCampaign" must {
    "return a single campaign from the database" when {
      "there is one campaign in the database" in new Setup(campaign) {
        await(repository.retrieveSingleCampaign(campaign.id)) mustBe Some(campaign)
      }
      "there are multiple campaigns in the database" in new Setup(campaign, campaign.copy(id = "updatedCampaignId")) {
        await(repository.retrieveSingleCampaign(campaign.id)) mustBe Some(campaign)
      }
    }
    "return nothing from the database" when {
      "there are no campaigns in the database" in new Setup {
        await(repository.retrieveSingleCampaign(campaign.id)) mustBe None
      }
      "the selected campaign is not in the database" in new Setup(campaign.copy(id = "updatedCampaignId")) {
        await(repository.retrieveSingleCampaign(campaign.id)) mustBe None
      }
    }
  }

  "insertCampaign" must {
    "insert a full campaign into the database and return back the campaign inserted" when {
      "the database does not contain any campaigns" in new Setup {
        await(repository.insertCampaign(campaign)) mustBe campaign
        findAll[Campaign](Campaign.reads) mustBe List(campaign)
      }
      "the database already contains a campaign" in new Setup(campaign) {
        await(repository.insertCampaign(campaign)) mustBe campaign
        findAll[Campaign](Campaign.reads) mustBe List(campaign, campaign)
      }
    }
  }

  "updateCampaign" must {
    "update the campaign on the database" in new Setup(campaign) {
      val updatedCampaign: Campaign = campaign.copy(name = "testUpdatedName")
      await(repository.updateCampaign(updatedCampaign)) mustBe Some(updatedCampaign)
    }
    "return none and not update any campaign" when {
      "there are no campaigns" in new Setup {
        await(repository.updateCampaign(campaign)) mustBe None
      }
      "the campaign to update is not in the database" in new Setup(campaign) {
        await(repository.updateCampaign(campaignMinimal.copy(id = "updatedCampaignId"))) mustBe None
      }
    }
  }

  "removeCampaign" must {
    "remove the campaign from the database" when {
      "it exists in the database" in new Setup(campaign) {
        await(repository.removeCampaign(campaign.id)) mustBe Some(campaign)
      }
    }
    "return none" when {
      "there are no records in the database" in new Setup {
        await(repository.removeCampaign(campaign.id)) mustBe None
      }
      "the campaign doesn't exist in the database" in new Setup(campaign) {
        await(repository.removeCampaign(campaignMinimal.id))
      }
    }
  }

}
