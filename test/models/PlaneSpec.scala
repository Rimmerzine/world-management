package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import utils.TestConstants

class PlaneSpec extends PlaySpec with TestConstants {

  "The Plane case class" must {
    "read from json" when {
      "the json is complete" in {
        Json.fromJson[Plane](testPlaneJson) mustBe JsSuccess(testPlane)
      }
      "the json is missing a description field" in {
        Json.fromJson[Plane](testPlaneMinimalJson) mustBe JsSuccess(testPlaneMinimal)
      }
    }
    "fail to read from json" when {
      "the campaignId is missing from json" in {
        val json: JsObject = Json.obj(
          "planeId" -> testPlaneId,
          "name" -> testPlaneName,
          "description" -> testPlaneDescription,
          "alignment" -> testPlaneAlignment
        )
        Json.fromJson[Plane](json) mustBe JsError(JsPath \ "campaignId", "error.path.missing")
      }
      "the planeId is missing from json" in {
        val json: JsObject = Json.obj(
          "campaignId" -> testCampaignId,
          "name" -> testPlaneName,
          "description" -> testPlaneDescription,
          "alignment" -> testPlaneAlignment
        )
        Json.fromJson[Plane](json) mustBe JsError(JsPath \ "planeId", "error.path.missing")
      }
      "the name is missing from json" in {
        val json: JsObject = Json.obj(
          "campaignId" -> testCampaignId,
          "planeId" -> testPlaneId,
          "description" -> testPlaneDescription,
          "alignment" -> testPlaneAlignment
        )
        Json.fromJson[Plane](json) mustBe JsError(JsPath \ "name", "error.path.missing")
      }
      "the alignment is missing from json" in {
        val json: JsObject = Json.obj(
          "campaignId" -> testCampaignId,
          "planeId" -> testPlaneId,
          "name" -> testPlaneName,
          "description" -> testPlaneDescription
        )
        Json.fromJson[Plane](json) mustBe JsError(JsPath \ "alignment", "error.path.missing")
      }
    }

    "write to json" when {
      "the model is full" in {
        Json.toJson(testPlane) mustBe testPlaneJson
      }
      "the description is not present" in {
        Json.toJson(testPlaneMinimal) mustBe testPlaneMinimalJson
      }
    }
  }

}
