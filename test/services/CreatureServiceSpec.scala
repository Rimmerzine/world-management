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
      val creatureList: List[Creature] = List(testCreature, testCreatureMinimal)
      when(mockCreatureRepository.retrieveCreatures(matches(None), matches(None))(any())) thenReturn Future.successful(creatureList)
      await(service.retrieveCreatures(None, None)) mustBe creatureList
    }
  }

  "retrieveSingleCreature" must {
    "return back the creature from the repository" in new Setup {
      when(mockCreatureRepository.retrieveSingleCreature(matches(testCreature.id))(any())) thenReturn Future.successful(Some(testCreature))
      await(service.retrieveSingleCreature(testCreature.id)) mustBe Some(testCreature)
    }
  }

  "createCreature" must {
    "return what is returned from the repository" in new Setup {
      when(mockCreatureRepository.insertCreature(matches(testCreature))(any())) thenReturn Future.successful(testCreature)
      await(service.createCreature(testCreature)) mustBe testCreature
    }
  }

  "updateCreature" must {
    "return what is returned from the repository" in new Setup {
      when(mockCreatureRepository.updateCreature(matches(testCreature))(any())) thenReturn Future.successful(Some(testCreature))
      await(service.updateCreature(testCreature)) mustBe Some(testCreature)
    }
  }

  "removeCreature" must {
    "return what is returned from the repository" in new Setup {
      when(mockCreatureRepository.removeCreature(matches(testCreature.id))(any())) thenReturn Future.successful(Some(testCreature))
      await(service.removeCreature(testCreature.id)) mustBe Some(testCreature)
    }
  }

}
