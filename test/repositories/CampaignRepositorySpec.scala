package repositories

import helpers.RepositorySpec
import models.Campaign
import play.modules.reactivemongo.ReactiveMongoApi
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class CampaignRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-campaigns"

  class Setup extends CampaignRepository {
    val reactiveApi: ReactiveMongoApi = testReactiveApi
    val databaseName: String = testDatabaseName
    val collectionName: String = testCollectionName
  }

  "retrieveCampaigns" must {
    "return campaigns from the database" when {
      "there is one campaign in the database" in new Setup {
        insertOne(testCampaign)
        await(retrieveCampaigns) mustBe List(testCampaign)
      }
      "there are multiple campaigns in the database" in new Setup {
        val campaigns: List[Campaign] = testCampaigns(5)
        insertMany(campaigns)
        await(retrieveCampaigns) mustBe campaigns
      }
    }
    "return nothing from the database" when {
      "there are no campaigns stored in the database" in new Setup {
        await(retrieveCampaigns) mustBe List.empty[Campaign]
      }
    }
  }

}
