package repositories

import config.AppConfig
import helpers.RepositorySpec
import models.Creature
import play.modules.reactivemongo.ReactiveMongoApi
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class CreatureRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-creatures"

  class Setup(repoCreatures: Creature*) {
    val repository: CreatureRepository = new CreatureRepository {
      val appConfig: AppConfig = testAppConfig
      val reactiveApi: ReactiveMongoApi = testReactiveApi
      val collectionName: String = testCollectionName
    }

    insertMany(repoCreatures.toList)
  }

  "retrieveCreatures" must {
    "return creatures from the database" when {
      "there is one creature in the database" in new Setup(testCreature) {
        await(repository.retrieveCreatures(None, None)) mustBe List(testCreature)
      }
      "there are multiple creatures in the database" in new Setup(testCreature, testCreatureMinimal) {
        await(repository.retrieveCreatures(None, None)) mustBe List(testCreature, testCreatureMinimal)
      }
      "there is a challenge rating filter" in new Setup(testCreature.copy(challengeRating = 50), testCreatureMinimal) {
        await(repository.retrieveCreatures(Some(50), None)) mustBe List(testCreature.copy(challengeRating = 50))
      }
      "there is a nameStart filter" in new Setup(testCreature.copy(name = "xSpecificName"), testCreature.copy(name = "XSpecificName")) {
        await(repository.retrieveCreatures(None, Some('x'))) mustBe List(testCreature.copy(name = "xSpecificName"), testCreature.copy(name = "XSpecificName"))
      }
    }
    "return nothing from the database" when {
      "there are no creatures stored in the database" in new Setup {
        await(repository.retrieveCreatures(None, None)) mustBe List.empty[Creature]
      }
      "there are no creatures that have the selected challenge rating" in new Setup(testCreature.copy(challengeRating = 10)) {
        await(repository.retrieveCreatures(Some(5), None)) mustBe List.empty[Creature]
      }
      "there are no creatures that match the name filter in the database" in new Setup(testCreature.copy(name = "aSpecificName")) {
        await(repository.retrieveCreatures(None, Some('x'))) mustBe List.empty[Creature]
      }
    }
  }

  "retrieveSingleCreature" must {
    "return a single creature" when {
      "there is one creature in the database" in new Setup(testCreature) {
        await(repository.retrieveSingleCreature(testCreature.id)) mustBe Some(testCreature)
      }
      "there are multiple creatures in the database" in new Setup(testCreature, testCreatureMinimal) {
        await(repository.retrieveSingleCreature(testCreature.id)) mustBe Some(testCreature)
      }
    }
    "return nothing from the database" when {
      "there are no creatures in the database" in new Setup {
        await(repository.retrieveSingleCreature(testCreature.id)) mustBe None
      }
      "the selected creature is not in the database" in new Setup(testCreature.copy(id = "otherId")) {
        await(repository.retrieveSingleCreature(testCreature.id)) mustBe None
      }
    }
  }

  "insertCreature" must {
    "insert a creature into the database and return the inserted crature" when {
      "there are no creatures in the database" in new Setup {
        await(repository.insertCreature(testCreature)) mustBe testCreature
        findAll[Creature] mustBe List(testCreature)
      }
      "the database already has a creature" in new Setup(testCreature) {
        await(repository.insertCreature(testCreature)) mustBe testCreature
        findAll[Creature] mustBe List(testCreature, testCreature)
      }
    }
  }

  "updateCreature" must {
    "update the creature on the database" in new Setup(testCreature) {
      val updatedCreature: Creature = testCreature.copy(name = "testUpdatedName")
      await(repository.updateCreature(updatedCreature)) mustBe Some(updatedCreature)
    }
    "return none and not update any creature" when {
      "there are no creatures" in new Setup {
        await(repository.updateCreature(testCreature)) mustBe None
      }
      "the creature to update is not in the database" in new Setup(testCreature) {
        await(repository.updateCreature(testCreature.copy(id = "otherId"))) mustBe None
      }
    }
  }

  "removeCreature" must {
    "remove the creature from the database" when {
      "it exists in the database" in new Setup(testCreature) {
        await(repository.removeCreature(testCreature.id)) mustBe Some(testCreature)
      }
    }
    "return none" when {
      "there are no records in the database" in new Setup {
        await(repository.removeCreature(testCreature.id)) mustBe None
      }
      "the creature doesn't exist in the database" in new Setup(testCreature) {
        await(repository.removeCreature("otherId")) mustBe None
      }
    }
  }

}
