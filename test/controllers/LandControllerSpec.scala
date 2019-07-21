package controllers

import helpers.UnitSpec
import models.Land
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsEmpty, ControllerComponents, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.LandService
import utils.TestConstants

import scala.concurrent.Future

class LandControllerSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockLandService: LandService = mock[LandService]

    val controller: LandController = new LandController {
      val landService: LandService = mockLandService
      protected def controllerComponents: ControllerComponents = stubControllerComponents()
    }
  }

  val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()
  val requestWithLandJson: FakeRequest[Land] = FakeRequest().withBody[Land](testLand)

  "getAllLands" must {
    s"return $OK" when {
      "lands are returned from the service" in new Setup {
        val landList: List[Land] = testLands(5)
        when(mockLandService.retrieveLands(matches(landList.head.planeId))(any())) thenReturn Future.successful(landList)

        val result: Future[Result] = controller.getAllLands(landList.head.planeId)(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(landList)
      }
    }

    s"return $NO_CONTENT" when {
      "no lands are returned from the service" in new Setup {
        val landList: List[Land] = List.empty[Land]
        when(mockLandService.retrieveLands(matches(testPlaneId))(any())) thenReturn Future.successful(landList)

        val result: Future[Result] = controller.getAllLands(testPlaneId)(request)
        status(result) mustBe NO_CONTENT
      }
    }
  }

  "getSingleLand" must {
    s"return $OK" when {
      "a land is returned from the service" in new Setup {
        when(mockLandService.retrieveSingleLand(matches(testLand.landId))(any())) thenReturn Future.successful(Some(testLand))

        val result: Future[Result] = controller.getSingleLand(testLand.landId)(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(testLand)
      }
    }

    s"return $NOT_FOUND" when {
      "no land was returned from the service" in new Setup {
        when(mockLandService.retrieveSingleLand(matches(testLand.landId))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.getSingleLand(testLand.landId)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "create" must {
    s"return $CREATED with a json body of the land created" when {
      "the service returns back the land created" in new Setup {
        when(mockLandService.createLand(matches(testLand))(any())) thenReturn Future.successful(testLand)

        val result: Future[Result] = controller.create(requestWithLandJson)
        status(result) mustBe CREATED
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe testLandJson
      }
    }
  }

  "update" must {
    s"return $OK with a json body of the updated land" when {
      "the service returns back a land" in new Setup {
        when(mockLandService.updateLand(matches(testLand))(any())) thenReturn Future.successful(Some(testLand))

        val result: Future[Result] = controller.update(requestWithLandJson)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe testLandJson
      }
    }

    s"return back $NOT_FOUND" when {
      "no land is returned back from the service" in new Setup {
        when(mockLandService.updateLand(matches(testLand))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.update(requestWithLandJson)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "remove" must {
    s"return $OK with a json body of the removed land" in new Setup {
      when(mockLandService.removeLand(matches(testLand.landId))(any())) thenReturn Future.successful(Some(testLand))

      val result: Future[Result] = controller.remove(testLand.landId)(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe testLandJson
    }

    s"return $NOT_FOUND" when {
      "no land is returned back from the service" in new Setup {
        when(mockLandService.removeLand(matches(testLand.landId))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.remove(testLand.landId)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

}
