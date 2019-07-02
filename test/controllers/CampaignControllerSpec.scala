package controllers

import helpers.UnitSpec
import models.Campaign
import play.api.mvc.{AnyContent, AnyContentAsJson, ControllerComponents, Request, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.CampaignService
import utils.TestConstants
import org.mockito.Mockito.when
import play.api.libs.json.Json
import org.mockito.ArgumentMatchers.{any, eq => matches}

import scala.concurrent.Future

class CampaignControllerSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockCampaignService: CampaignService = mock[CampaignService]

    val controller: CampaignController = new CampaignController {
      val campaignService: CampaignService = mockCampaignService
      protected def controllerComponents: ControllerComponents = stubControllerComponents()
    }
  }

  val request: Request[AnyContent] = FakeRequest()
  val requestWithCampaignJson: Request[Campaign] = FakeRequest().withBody[Campaign](testCampaign)

  "campaigns" must {
    s"return $OK" when {
      "campaigns are returned from the service" in new Setup {
        val campaignList: List[Campaign] = testCampaigns(5)
        when(mockCampaignService.retrieveCampaigns(any())) thenReturn Future.successful(campaignList)

        val result: Future[Result] = controller.campaigns(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(campaignList)
      }
    }

    s"return $NO_CONTENT" when {
      "no campaigns are returned from the service" in new Setup {
        val campaignList: List[Campaign] = List.empty[Campaign]
        when(mockCampaignService.retrieveCampaigns(any())) thenReturn Future.successful(campaignList)

        val result: Future[Result] = controller.campaigns(request)
        status(result) mustBe NO_CONTENT
      }
    }
  }

  "create" must {
    s"return $CREATED with a json body of the campaign created" when {
      "the service returns back the campaign created" in new Setup {
        when(mockCampaignService.createCampaign(matches(testCampaign))(any())) thenReturn Future.successful(testCampaign)

        val result: Future[Result] = controller.create(requestWithCampaignJson)
        status(result) mustBe CREATED
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe testCampaignJson
      }
    }
  }

  "update" must {
    s"return $OK with a json body of the updated campaign" when {
      "the service returns back a campaign" in new Setup {
        when(mockCampaignService.updateCampaign(matches(testCampaign))(any())) thenReturn Future.successful(Some(testCampaign))

        val result: Future[Result] = controller.update(requestWithCampaignJson)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe testCampaignJson
      }
    }

    s"return back $NOT_FOUND" when {
      "no campaign is returned back from the service" in new Setup {
        when(mockCampaignService.updateCampaign(matches(testCampaign))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.update(requestWithCampaignJson)
        status(result) mustBe NOT_FOUND
      }
    }
  }

}
