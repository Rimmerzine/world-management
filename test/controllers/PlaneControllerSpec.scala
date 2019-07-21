package controllers

import helpers.UnitSpec
import models.Plane
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsEmpty, ControllerComponents, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.PlaneService
import utils.TestConstants

import scala.concurrent.Future

class PlaneControllerSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockPlaneService: PlaneService = mock[PlaneService]

    val controller: PlaneController = new PlaneController {
      val planeService: PlaneService = mockPlaneService
      protected def controllerComponents: ControllerComponents = stubControllerComponents()
    }
  }

  val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()
  val requestWithPlaneJson: FakeRequest[Plane] = FakeRequest().withBody[Plane](testPlane)

  "getAllPlanes" must {
    s"return $OK" when {
      "planes are returned from the service" in new Setup {
        val planeList: List[Plane] = testPlanes(5)
        when(mockPlaneService.retrievePlanes(matches(planeList.head.campaignId))(any())) thenReturn Future.successful(planeList)

        val result: Future[Result] = controller.getAllPlanes(planeList.head.campaignId)(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(planeList)
      }
    }

    s"return $NO_CONTENT" when {
      "no planes are returned from the service" in new Setup {
        val planeList: List[Plane] = List.empty[Plane]
        when(mockPlaneService.retrievePlanes(matches(testCampaignId))(any())) thenReturn Future.successful(planeList)

        val result: Future[Result] = controller.getAllPlanes(testCampaignId)(request)
        status(result) mustBe NO_CONTENT
      }
    }
  }

  "getSinglePlane" must {
    s"return $OK" when {
      "a plane is returned from the service" in new Setup {
        when(mockPlaneService.retrieveSinglePlane(matches(testPlane.planeId))(any())) thenReturn Future.successful(Some(testPlane))

        val result: Future[Result] = controller.getSinglePlane(testPlane.planeId)(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(testPlane)
      }
    }

    s"return $NOT_FOUND" when {
      "no plane was returned from the service" in new Setup {
        when(mockPlaneService.retrieveSinglePlane(matches(testPlane.planeId))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.getSinglePlane(testPlane.planeId)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "create" must {
    s"return $CREATED with a json body of the plane created" when {
      "the service returns back the plane created" in new Setup {
        when(mockPlaneService.createPlane(matches(testPlane))(any())) thenReturn Future.successful(testPlane)

        val result: Future[Result] = controller.create(requestWithPlaneJson)
        status(result) mustBe CREATED
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe testPlaneJson
      }
    }
  }

  "update" must {
    s"return $OK with a json body of the updated plane" when {
      "the service returns back a plane" in new Setup {
        when(mockPlaneService.updatePlane(matches(testPlane))(any())) thenReturn Future.successful(Some(testPlane))

        val result: Future[Result] = controller.update(requestWithPlaneJson)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe testPlaneJson
      }
    }

    s"return back $NOT_FOUND" when {
      "no plane is returned back from the service" in new Setup {
        when(mockPlaneService.updatePlane(matches(testPlane))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.update(requestWithPlaneJson)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "remove" must {
    s"return $OK with a json body of the removed plane" in new Setup {
      when(mockPlaneService.removePlane(matches(testPlane.planeId))(any())) thenReturn Future.successful(Some(testPlane))

      val result: Future[Result] = controller.remove(testPlane.planeId)(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe testPlaneJson
    }

    s"return $NOT_FOUND" when {
      "no plane is returned back from the service" in new Setup {
        when(mockPlaneService.removePlane(matches(testPlane.planeId))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.remove(testPlane.planeId)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

}
