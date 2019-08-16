package models

import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import utils.WorldElementConstants

class WorldElementSpec extends PlaySpec with WorldElementConstants {

  "WorldElement - Campaign" must {
    "read a campaign from json successfully" when {
      "the json is complete" in {
        Json.fromJson[WorldElement](campaignJson) mustBe JsSuccess(campaign)
      }
      "the json is missing optional fields" in {
        Json.fromJson[WorldElement](campaignMinimalJson) mustBe JsSuccess(campaignMinimal)
      }
    }
    "fail to read a campaign from json" when {
      "elementType is missing from the json" in {
        Json.fromJson[WorldElement](campaignJson - "elementType") mustBe JsError(JsPath \ "elementType", "error.invalid")
      }

      List("id", "name", "content") foreach { key =>
        s"$key is missing from the json" in {
          Json.fromJson[WorldElement](campaignJson - key) mustBe JsError(JsPath \ key, "error.path.missing")
        }
      }
    }

    "write a campaign to json" when {
      "the model is complete" in {
        Json.toJson(campaign)(WorldElement.format) mustBe campaignJson
      }
      "the model is missing optional fields" in {
        Json.toJson(campaignMinimal)(WorldElement.format) mustBe campaignMinimalJson
      }
    }
  }

  "WorldElement - Plane" must {
    "read a plane from json successfully" when {
      "the json is complete" in {
        Json.fromJson[WorldElement](planeJson) mustBe JsSuccess(plane)
      }
      "the json is missing optional fields" in {
        Json.fromJson[WorldElement](planeMinimalJson) mustBe JsSuccess(planeMinimal)
      }
    }
    "fail to read a plane from json" when {
      "elementType is missing from the json" in {
        Json.fromJson[WorldElement](planeJson - "elementType") mustBe JsError(JsPath \ "elementType", "error.invalid")
      }

      List("id", "name", "content", "alignment") foreach { key =>
        s"$key is missing from the json" in {
          Json.fromJson[WorldElement](planeJson - key) mustBe JsError(JsPath \ key, "error.path.missing")
        }
      }
    }

    "write a plane to json" when {
      "the model is complete" in {
        Json.toJson(plane)(WorldElement.format) mustBe planeJson
      }
      "the model is missing optional fields" in {
        Json.toJson(planeMinimal)(WorldElement.format) mustBe planeMinimalJson
      }
    }
  }

  "WorldElement - Land" must {
    "read a land from json successfully" when {
      "the json is complete" in {
        Json.fromJson[WorldElement](landJson) mustBe JsSuccess(land)
      }
      "the json is missing optional fields" in {
        Json.fromJson[WorldElement](landMinimalJson) mustBe JsSuccess(landMinimal)
      }
    }
    "fail to read a land from json" when {
      "elementType is missing from the json" in {
        Json.fromJson[WorldElement](landJson - "elementType") mustBe JsError(JsPath \ "elementType", "error.invalid")
      }

      List("id", "name", "content") foreach { key =>
        s"$key is missing from the json" in {
          Json.fromJson[WorldElement](landJson - key) mustBe JsError(JsPath \ key, "error.path.missing")
        }
      }
    }

    "write a land to json" when {
      "the model is complete" in {
        Json.toJson(land)(WorldElement.format) mustBe landJson
      }
      "the model is missing optional fields" in {
        Json.toJson(landMinimal)(WorldElement.format) mustBe landMinimalJson
      }
    }
  }

}
