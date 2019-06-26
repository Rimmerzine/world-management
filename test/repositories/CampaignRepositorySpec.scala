package repositories

import helpers.RepositorySpec
import models.Campaign
import play.modules.reactivemongo.ReactiveMongoApi
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class CampaignRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-campaigns"

  class Setup(repoCampaigns: Campaign*) {
    val repository: CampaignRepository = new CampaignRepository {
      val reactiveApi: ReactiveMongoApi = testReactiveApi
      val databaseName: String = testDatabaseName
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

}
