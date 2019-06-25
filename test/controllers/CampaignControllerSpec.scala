package controllers

import helpers.UnitSpec
import models.Campaign
import play.api.mvc.{AnyContent, ControllerComponents, Request, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.CampaignService
import utils.TestConstants
import org.mockito.Mockito.when
import play.api.libs.json.Json

import scala.concurrent.Future

class CampaignControllerSpec extends UnitSpec with TestConstants {

  class Setup extends CampaignController {
    val campaignService: CampaignService = mock[CampaignService]
    protected def controllerComponents: ControllerComponents = stubControllerComponents()
  }

  val request: Request[AnyContent] = FakeRequest()

  "campaigns" must {
    s"return $OK" when {
      "campaigns are returned from the service" in new Setup {
        val campaignList: List[Campaign] = testCampaigns(5)
        when(campaignService.campaigns) thenReturn Future.successful(campaignList)

        val result: Future[Result] = campaigns(request)
        status(result) mustBe OK
        contentType(result) mustBe Some("application/json")
        contentAsJson(result) mustBe Json.toJson(campaignList)
      }
    }

    s"return $NO_CONTENT" when {
      "no campaigns are returned from the service" in new Setup {
        val campaignList: List[Campaign] = List.empty[Campaign]
        when(campaignService.campaigns) thenReturn Future.successful(campaignList)

        val result: Future[Result] = campaigns(request)
        status(result) mustBe NO_CONTENT
      }
    }
  }

}
