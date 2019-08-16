package repositories

import config.AppConfig
import helpers.RepositorySpec
import models.WorldElement
import play.modules.reactivemongo.ReactiveMongoApi
import utils.WorldElementConstants

import scala.concurrent.ExecutionContext.Implicits.global

class WorldElementRepositorySpec extends RepositorySpec with WorldElementConstants {

  val testCollectionName: String = "test-world-elements"

  class Setup(repoWorldElements: WorldElement*) {
    val repository: WorldElementRepository = new WorldElementRepository {
      val appConfig: AppConfig = testAppConfig
      val reactiveApi: ReactiveMongoApi = testReactiveApi
      val collectionName: String = testCollectionName
    }

    insertMany(repoWorldElements.toList)
  }

  "retrieveWorldElements" must {
    "return world elements from the database" when {
      "there is one world elements in the database" in new Setup(campaign) {
        await(repository.retrieveWorldElements) mustBe List(campaign)
      }
      "there are multiple campaigns in the database" in new Setup(campaign, campaignMinimal) {
        await(repository.retrieveWorldElements) mustBe List(campaign, campaignMinimal)
      }
    }
    "return nothing from the database" when {
      "there are no campaigns stored in the database" in new Setup {
        await(repository.retrieveWorldElements) mustBe List.empty[WorldElement]
      }
    }
  }

  "retrieveSingleWorldElement" must {
    "return a single world element from the database" when {
      "there is one world element in the database" in new Setup(campaign) {
        await(repository.retrieveSingleWorldElement(campaign.id)) mustBe Some(campaign)
      }
      "there are multiple world element in the database" in new Setup(campaignMinimal.copy(id = "testDifferentId"), campaign) {
        await(repository.retrieveSingleWorldElement(campaign.id)) mustBe Some(campaign)
      }
    }
    "return nothing from the database" when {
      "there are no world element in the database" in new Setup {
        await(repository.retrieveSingleWorldElement(campaign.id)) mustBe None
      }
      "the selected world element is not in the database" in new Setup(campaignMinimal.copy(id = "testDifferentId")) {
        await(repository.retrieveSingleWorldElement(campaign.id)) mustBe None
      }
    }
  }

  "insertWorldElement" must {
    "insert a full world element into the database and return back the world element inserted" when {
      "the database does not contain any world elements" in new Setup {
        await(repository.insertWorldElement(campaign)) mustBe campaign
        findAll[WorldElement] mustBe List(campaign)
      }
      "the database already contains a world element" in new Setup(campaign) {
        await(repository.insertWorldElement(campaign)) mustBe campaign
        findAll[WorldElement] mustBe List(campaign, campaign)
      }
    }
  }

  "updateWorldElement" must {
    "update the world element on the database" in new Setup(campaign) {
      val updatedWorldElement: WorldElement = campaign.copy(name = "testUpdatedName")
      await(repository.updateWorldElement(updatedWorldElement)) mustBe Some(updatedWorldElement)
    }
    "return none and not update any world element" when {
      "there are no world elements" in new Setup {
        await(repository.updateWorldElement(campaign)) mustBe None
      }
      "the world element to update is not in the database" in new Setup(campaign.copy(id = "testDifferentId")) {
        await(repository.updateWorldElement(campaignMinimal)) mustBe None
      }
    }
  }

  "removeWorldElement" must {
    "remove the world element from the database" when {
      "it exists in the database" in new Setup(campaign) {
        await(repository.removeWorldElement(campaign.id)) mustBe Some(campaign)
      }
    }
    "return none" when {
      "there are no records in the database" in new Setup {
        await(repository.removeWorldElement(campaign.id)) mustBe None
      }
      "the world element doesn't exist in the database" in new Setup(campaign) {
        await(repository.removeWorldElement(campaignMinimal.id))
      }
    }
  }

}
