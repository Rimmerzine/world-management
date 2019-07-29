package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import utils.TestConstants

class CampaignSpec extends PlaySpec with TestConstants {

  "The Campaign case class" must {
    "read from json" when {
      "the json is complete" in {
        Json.fromJson[Campaign](testCampaignJson) mustBe JsSuccess(testCampaign)
      }
      "the json is missing a description field" in {
        Json.fromJson[Campaign](testCampaignMinimalJson) mustBe JsSuccess(testCampaignMinimal)
      }
    }
    "fail to read from json" when {
      "the id is missing from json" in {
        val json: JsObject = Json.obj("name" -> testCampaignName, "description" -> testCampaignDescription)
        Json.fromJson[Campaign](json) mustBe JsError(JsPath \ "id", "error.path.missing")
      }
      "the name is missing from json" in {
        val json: JsObject = Json.obj("id" -> testCampaignId, "description" -> testCampaignDescription)
        Json.fromJson[Campaign](json) mustBe JsError(JsPath \ "name", "error.path.missing")
      }
    }

    "write to json" when {
      "the model is full" in {
        Json.toJson(testCampaign) mustBe testCampaignJson
      }
      "the description is not present" in {
        Json.toJson(testCampaignMinimal) mustBe testCampaignMinimalJson
      }
    }
  }

}
