package controllers

import helpers.UnitSpec
import models.Creature
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsEmpty, ControllerComponents, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.CreatureService
import utils.TestConstants

import scala.concurrent.Future

class CreatureControllerSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockCreatureService: CreatureService = mock[CreatureService]

    val controller: CreatureController = new CreatureController {
      val creatureService: CreatureService = mockCreatureService

      protected def controllerComponents: ControllerComponents = stubControllerComponents()
    }
  }

  val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()
  val requestWithCreatureJson: FakeRequest[Creature] = FakeRequest().withBody[Creature](creature)

  "getAllCreatures" must {
    s"return $OK" when {
      "creatures are returned from the service" in new Setup {
        val creatureList: List[Creature] = List(creature, creatureMinimal)
        when(mockCreatureService.retrieveCreatures(any())) thenReturn Future.successful(creatureList)

        val result: Future[Result] = controller.getAllCreatures(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(creatureList)
      }
    }
    s"return $NO_CONTENT" when {
      "no creatures are returned from the service" in new Setup {
        when(mockCreatureService.retrieveCreatures(any())) thenReturn Future.successful(List.empty[Creature])

        val result: Future[Result] = controller.getAllCreatures(request)
        status(result) mustBe NO_CONTENT
      }
    }
  }

  "getSingleCreature" must {
    s"return $OK" when {
      "a creature is returned from the service" in new Setup {
        when(mockCreatureService.retrieveSingleCreature(matches(creature.id))(any())) thenReturn Future.successful(Some(creature))

        val result: Future[Result] = controller.getSingleCreature(creature.id)(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(creature)
      }
    }
    s"return $NOT_FOUND" when {
      "no creature is returned from the service" in new Setup {
        when(mockCreatureService.retrieveSingleCreature(matches(creature.id))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.getSingleCreature(creature.id)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "create" must {
    s"return $CREATED" when {
      "the service returns back the created creature" in new Setup {
        when(mockCreatureService.createCreature(matches(creature))(any())) thenReturn Future.successful(creature)

        val result: Future[Result] = controller.create(requestWithCreatureJson)
        status(result) mustBe CREATED
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(creature)
      }
    }
  }

  "update" must {
    s"return $OK" when {
      "the service returns back the updated creature" in new Setup {
        when(mockCreatureService.updateCreature(matches(creature))(any())) thenReturn Future.successful(Some(creature))

        val result: Future[Result] = controller.update(requestWithCreatureJson)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(creature)
      }
    }
    s"return $NOT_FOUND" when {
      "no creature is returned from the service" in new Setup {
        when(mockCreatureService.updateCreature(matches(creature))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.update(requestWithCreatureJson)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "remove" must {
    s"return $OK with a json body of the removed creature" in new Setup {
      when(mockCreatureService.removeCreature(matches(creature.id))(any())) thenReturn Future.successful(Some(creature))

      val result: Future[Result] = controller.remove(creature.id)(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe Json.toJson(creature)
    }

    s"return $NOT_FOUND" when {
      "no creature is returned back from the service" in new Setup {
        when(mockCreatureService.removeCreature(matches(creature.id))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.remove(creature.id)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

}
