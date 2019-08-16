package controllers

import helpers.UnitSpec
import models.WorldElement
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsEmpty, ControllerComponents, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.WorldElementService
import utils.WorldElementConstants

import scala.concurrent.Future

class WorldElementControllerSpec extends UnitSpec with WorldElementConstants {

  class Setup {
    val mockWorldElementService: WorldElementService = mock[WorldElementService]

    val controller: WorldElementController = new WorldElementController {
      val worldElementService: WorldElementService = mockWorldElementService

      protected def controllerComponents: ControllerComponents = stubControllerComponents()
    }
  }

  val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()
  val requestWithWorldElementJson: FakeRequest[WorldElement] = FakeRequest().withBody[WorldElement](campaign)

  "getAllWorldElements" must {
    s"return $OK" when {
      "world elements are returned from the service" in new Setup {
        val worldElementList: List[WorldElement] = List(campaign, campaignMinimal)
        when(mockWorldElementService.retrieveWorldElements(any())) thenReturn Future.successful(worldElementList)

        val result: Future[Result] = controller.getAllWorldElements(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(worldElementList)
      }
    }

    s"return $NO_CONTENT" when {
      "no world elements are returned from the service" in new Setup {
        val worldElementList: List[WorldElement] = List.empty[WorldElement]
        when(mockWorldElementService.retrieveWorldElements(any())) thenReturn Future.successful(worldElementList)

        val result: Future[Result] = controller.getAllWorldElements(request)
        status(result) mustBe NO_CONTENT
      }
    }
  }

  "getSingleWorldElement" must {
    s"return $OK" when {
      "a world element is returned from the service" in new Setup {
        when(mockWorldElementService.retrieveSingleWorldElement(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))

        val result: Future[Result] = controller.getSingleWorldElement(campaign.id)(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(campaign)
      }
    }

    s"return $NOT_FOUND" when {
      "no world element was returned from the service" in new Setup {
        when(mockWorldElementService.retrieveSingleWorldElement(matches(campaign.id))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.getSingleWorldElement(campaign.id)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "create" must {
    s"return $CREATED with a json body of the world element created" when {
      "the service returns back the world element created" in new Setup {
        when(mockWorldElementService.createWorldElement(matches(campaign))(any())) thenReturn Future.successful(campaign)

        val result: Future[Result] = controller.create(requestWithWorldElementJson)
        status(result) mustBe CREATED
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe campaignJson
      }
    }
  }

  "update" must {
    s"return $OK with a json body of the updated world element" when {
      "the service returns back a world element" in new Setup {
        when(mockWorldElementService.updateWorldElement(matches(campaign))(any())) thenReturn Future.successful(Some(campaign))

        val result: Future[Result] = controller.update(requestWithWorldElementJson)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe campaignJson
      }
    }

    s"return back $NOT_FOUND" when {
      "no world element is returned back from the service" in new Setup {
        when(mockWorldElementService.updateWorldElement(matches(campaign))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.update(requestWithWorldElementJson)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "remove" must {
    s"return $OK with a json body of the removed world element" in new Setup {
      when(mockWorldElementService.removeWorldElement(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))

      val result: Future[Result] = controller.remove(campaign.id)(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe campaignJson
    }

    s"return $NOT_FOUND" when {
      "no world element is returned back from the service" in new Setup {
        when(mockWorldElementService.removeWorldElement(matches(campaign.id))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.remove(campaign.id)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

}
