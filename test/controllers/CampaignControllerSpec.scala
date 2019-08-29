package controllers

import helpers.UnitSpec
import models.Campaign
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import play.api.libs.json.Json
import play.api.mvc.{AnyContentAsEmpty, ControllerComponents, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.CampaignService
import utils.TestConstants

import scala.concurrent.Future

class CampaignControllerSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockCampaignService: CampaignService = mock[CampaignService]

    val controller: CampaignController = new CampaignController {
      val campaignService: CampaignService = mockCampaignService

      protected def controllerComponents: ControllerComponents = stubControllerComponents()
    }
  }

  val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()
  val requestWithCampaignJson: FakeRequest[Campaign] = FakeRequest().withBody[Campaign](campaign)

  "getAllCampaigns" must {
    s"return $OK" when {
      "campaigns are returned from the service" in new Setup {
        val campaignList: List[Campaign] = List(campaign, campaignMinimal)
        when(mockCampaignService.retrieveCampaigns(any())) thenReturn Future.successful(campaignList)

        val result: Future[Result] = controller.getAllCampaigns(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(campaignList)
      }
    }

    s"return $NO_CONTENT" when {
      "no campaigns are returned from the service" in new Setup {
        val campaignList: List[Campaign] = List.empty[Campaign]
        when(mockCampaignService.retrieveCampaigns(any())) thenReturn Future.successful(campaignList)

        val result: Future[Result] = controller.getAllCampaigns(request)
        status(result) mustBe NO_CONTENT
      }
    }
  }

  "getSingleCampaign" must {
    s"return $OK" when {
      "a campaign is returned from the service" in new Setup {
        when(mockCampaignService.retrieveSingleCampaign(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))

        val result: Future[Result] = controller.getSingleCampaign(campaign.id)(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(campaign)
      }
    }

    s"return $NOT_FOUND" when {
      "no campaign was returned from the service" in new Setup {
        when(mockCampaignService.retrieveSingleCampaign(matches(campaign.id))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.getSingleCampaign(campaign.id)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "create" must {
    s"return $CREATED with a json body of the campaign created" when {
      "the service returns back the campaign created" in new Setup {
        when(mockCampaignService.createCampaign(matches(campaign))(any())) thenReturn Future.successful(campaign)

        val result: Future[Result] = controller.create(requestWithCampaignJson)
        status(result) mustBe CREATED
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe campaignJson
      }
    }
  }

  "update" must {
    s"return $OK with a json body of the updated campaign" when {
      "the service returns back a campaign" in new Setup {
        when(mockCampaignService.updateCampaign(matches(campaign))(any())) thenReturn Future.successful(Some(campaign))

        val result: Future[Result] = controller.update(requestWithCampaignJson)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe campaignJson
      }
    }

    s"return back $NOT_FOUND" when {
      "no campaign is returned back from the service" in new Setup {
        when(mockCampaignService.updateCampaign(matches(campaign))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.update(requestWithCampaignJson)
        status(result) mustBe NOT_FOUND
      }
    }
  }

  "remove" must {
    s"return $OK with a json body of the removed campaign" in new Setup {
      when(mockCampaignService.removeCampaign(matches(campaign.id))(any())) thenReturn Future.successful(Some(campaign))

      val result: Future[Result] = controller.remove(campaign.id)(request)
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      contentAsJson(result) mustBe campaignJson
    }

    s"return $NOT_FOUND" when {
      "no campaign is returned back from the service" in new Setup {
        when(mockCampaignService.removeCampaign(matches(campaign.id))(any())) thenReturn Future.successful(None)

        val result: Future[Result] = controller.remove(campaign.id)(request)
        status(result) mustBe NOT_FOUND
      }
    }
  }

}
