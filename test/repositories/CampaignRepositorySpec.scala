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
      "there is one campaign in the database" in new Setup(testCampaign) {
        await(repository.retrieveCampaigns) mustBe List(testCampaign)
      }
      "there are multiple campaigns in the database" in new Setup(testCampaigns(5): _*) {
        await(repository.retrieveCampaigns) mustBe testCampaigns(5)
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
      "there is one campaign in the database" in new Setup(testCampaign) {
        await(repository.retrieveSingleCampaign(testCampaign.id)) mustBe Some(testCampaign)
      }
      "there are multiple campaigns in the database" in new Setup(testCampaignMinimal, testCampaign) {
        await(repository.retrieveSingleCampaign(testCampaign.id)) mustBe Some(testCampaign)
      }
    }
    "return nothing from the database" when {
      "there are no campaigns in the database" in new Setup {
        await(repository.retrieveSingleCampaign(testCampaign.id)) mustBe None
      }
      "the selected campaign is not in the database" in new Setup(testCampaignMinimal) {
        await(repository.retrieveSingleCampaign(testCampaign.id)) mustBe None
      }
    }
  }

  "insertCampaign" must {
    "insert a full campaign into the database and return back the campaign inserted" when {
      "the database does not contain any campaigns" in new Setup {
        await(repository.insertCampaign(testCampaign)) mustBe testCampaign
        findAll[Campaign] mustBe List(testCampaign)
      }
      "the database already contains a campaign" in new Setup(testCampaign) {
        await(repository.insertCampaign(testCampaign)) mustBe testCampaign
        findAll[Campaign] mustBe List(testCampaign, testCampaign)
      }
    }
  }

  "updateCampaign" must {
    "update the campaign on the database" in new Setup(testCampaign) {
      val updatedCampaign: Campaign = testCampaign.copy(name = "testUpdatedName")
      await(repository.updateCampaign(updatedCampaign)) mustBe Some(updatedCampaign)
    }
    "return none and not update any campaign" when {
      "there are no campaigns" in new Setup {
        await(repository.updateCampaign(testCampaign)) mustBe None
      }
      "the campaign to update is not in the database" in new Setup(testCampaign) {
        await(repository.updateCampaign(testCampaignMinimal)) mustBe None
      }
    }
  }

  "removeCampaign" must {
    "remove the campaign from the database" when {
      "it exists in the database" in new Setup(testCampaign) {
        await(repository.removeCampaign(testCampaign.id)) mustBe Some(testCampaign)
      }
    }
    "return none" when {
      "there are no records in the database" in new Setup {
        await(repository.removeCampaign(testCampaign.id)) mustBe None
      }
      "the campaign doesn't exist in the database" in new Setup(testCampaign) {
        await(repository.removeCampaign(testCampaignMinimal.id))
      }
    }
  }

}
