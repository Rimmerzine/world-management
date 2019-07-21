package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import utils.TestConstants

class LandSpec extends PlaySpec with TestConstants {

  "The Land case class" must {
    "read from json" when {
      "the json is complete" in {
        Json.fromJson[Land](testLandJson) mustBe JsSuccess(testLand)
      }
      "the json is missing a description field" in {
        Json.fromJson[Land](testLandMinimalJson) mustBe JsSuccess(testLandMinimal)
      }
    }
    "fail to read from json" when {
      "the planeId is missing from json" in {
        val json: JsObject = Json.obj("landId" -> testLandId, "name" -> testLandName, "description" -> testLandDescription)
        Json.fromJson[Land](json) mustBe JsError(JsPath \ "planeId", "error.path.missing")
      }
      "the landId is missing from json" in {
        val json: JsObject = Json.obj("planeId" -> testPlaneId, "name" -> testLandName, "description" -> testLandDescription)
        Json.fromJson[Land](json) mustBe JsError(JsPath \ "landId", "error.path.missing")
      }
      "the name is missing from json" in {
        val json: JsObject = Json.obj("planeId" -> testPlaneId, "landId" -> testLandId, "description" -> testLandDescription)
        Json.fromJson[Land](json) mustBe JsError(JsPath \ "name", "error.path.missing")
      }
    }

    "write to json" when {
      "the model is full" in {
        Json.toJson(testLand) mustBe testLandJson
      }
      "the description is not present" in {
        Json.toJson(testLandMinimal) mustBe testLandMinimalJson
      }
    }
  }

}
