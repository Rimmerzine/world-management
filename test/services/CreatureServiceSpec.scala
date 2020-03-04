package services

import helpers.UnitSpec
import models.Creature
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import repositories.CreatureRepository
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CreatureServiceSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockCreatureRepository: CreatureRepository = mock[CreatureRepository]

    val service: CreatureService = new CreatureService {
      val creatureRepository: CreatureRepository = mockCreatureRepository
    }
  }

  "retrieveCreatures" must {
    "return back the creatures from the repository" in new Setup {
      val creatureList: List[Creature] = List(creature, creatureMinimal)
      when(mockCreatureRepository.retrieveCreatures(any())) thenReturn Future.successful(creatureList)
      await(service.retrieveCreatures) mustBe creatureList
    }
  }

  "retrieveSingleCreature" must {
    "return back the creature from the repository" in new Setup {
      when(mockCreatureRepository.retrieveSingleCreature(matches(creature.id))(any())) thenReturn Future.successful(Some(creature))
      await(service.retrieveSingleCreature(creature.id)) mustBe Some(creature)
    }
  }

  "createCreature" must {
    "return what is returned from the repository" in new Setup {
      when(mockCreatureRepository.insertCreature(matches(creature))(any())) thenReturn Future.successful(creature)
      await(service.createCreature(creature)) mustBe creature
    }
  }

  "updateCreature" must {
    "return what is returned from the repository" in new Setup {
      when(mockCreatureRepository.updateCreature(matches(creature))(any())) thenReturn Future.successful(Some(creature))
      await(service.updateCreature(creature)) mustBe Some(creature)
    }
  }

  "removeCreature" must {
    "return what is returned from the repository" in new Setup {
      when(mockCreatureRepository.removeCreature(matches(creature.id))(any())) thenReturn Future.successful(Some(creature))
      await(service.removeCreature(creature.id)) mustBe Some(creature)
    }
  }

}
