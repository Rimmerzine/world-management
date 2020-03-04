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
      "there is one creature in the database" in new Setup(creature) {
        await(repository.retrieveCreatures) mustBe List(creature)
      }
      "there are multiple creatures in the database" in new Setup(creature, creatureMinimal) {
        await(repository.retrieveCreatures) mustBe List(creature, creatureMinimal)
      }
    }
    "return nothing from the database" when {
      "there are no creatures stored in the database" in new Setup {
        await(repository.retrieveCreatures) mustBe List.empty[Creature]
      }
    }
  }

  "retrieveSingleCreature" must {
    "return a single creature" when {
      "there is one creature in the database" in new Setup(creature) {
        await(repository.retrieveSingleCreature(creature.id)) mustBe Some(creature)
      }
      "there are multiple creatures in the database" in new Setup(creature, creatureMinimal) {
        await(repository.retrieveSingleCreature(creature.id)) mustBe Some(creature)
      }
    }
    "return nothing from the database" when {
      "there are no creatures in the database" in new Setup {
        await(repository.retrieveSingleCreature(creature.id)) mustBe None
      }
      "the selected creature is not in the database" in new Setup(creature.copy(id = "otherId")) {
        await(repository.retrieveSingleCreature(creature.id)) mustBe None
      }
    }
  }

  "insertCreature" must {
    "insert a creature into the database and return the inserted crature" when {
      "there are no creatures in the database" in new Setup {
        await(repository.insertCreature(creature)) mustBe creature
        findAll[Creature] mustBe List(creature)
      }
      "the database already has a creature" in new Setup(creature) {
        await(repository.insertCreature(creature)) mustBe creature
        findAll[Creature] mustBe List(creature, creature)
      }
    }
  }

  "updateCreature" must {
    "update the creature on the database" in new Setup(creature) {
      val updatedCreature: Creature = creature.copy(detail = creature.detail.copy(name = "testUpdatedName"))
      await(repository.updateCreature(updatedCreature)) mustBe Some(updatedCreature)
    }
    "return none and not update any creature" when {
      "there are no creatures" in new Setup {
        await(repository.updateCreature(creature)) mustBe None
      }
      "the creature to update is not in the database" in new Setup(creature) {
        await(repository.updateCreature(creature.copy(id = "otherId"))) mustBe None
      }
    }
  }

  "removeCreature" must {
    "remove the creature from the database" when {
      "it exists in the database" in new Setup(creature) {
        await(repository.removeCreature(creature.id)) mustBe Some(creature)
      }
    }
    "return none" when {
      "there are no records in the database" in new Setup {
        await(repository.removeCreature(creature.id)) mustBe None
      }
      "the creature doesn't exist in the database" in new Setup(creature) {
        await(repository.removeCreature("otherId")) mustBe None
      }
    }
  }

}
