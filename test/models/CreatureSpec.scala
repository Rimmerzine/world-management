package models

import helpers.UnitSpec
import play.api.libs.json._
import utils.TestConstants

class CreatureSpec extends UnitSpec with TestConstants {

  private def prunePathFromJson(json: JsObject, path: JsPath): JsObject = json.transform(path.json.prune).get

  "The Creature case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[Creature](testCreatureJson) mustBe JsSuccess(testCreature)
      }
      "the json is minimal" in {
        Json.fromJson[Creature](testCreatureMinimalJson) mustBe JsSuccess(testCreatureMinimal)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "id",
        JsPath \ "name",
        JsPath \ "size",
        JsPath \ "alignment",
        JsPath \ "armourClass",
        JsPath \ "hitPoints",
        JsPath \ "creatureType",
        JsPath \ "challengeRating"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureJson, path)
          Json.fromJson[Creature](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The MovementSpeed case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[MovementSpeed](testCreatureMovementSpeedJson) mustBe JsSuccess(testCreatureMovementSpeed)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "value"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureMovementSpeedJson, path)
          Json.fromJson[MovementSpeed](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The AbilityScore case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[AbilityScore](testCreatureAbilityScoreJson) mustBe JsSuccess(testCreatureAbilityScore)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "value",
        JsPath \ "proficient"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureAbilityScoreJson, path)
          Json.fromJson[AbilityScore](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The SkillProficiency case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[SkillProficiency](testCreatureSkillProficiencyJson) mustBe JsSuccess(testCreatureSkillProficiency)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "value"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureSkillProficiencyJson, path)
          Json.fromJson[SkillProficiency](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The Sense case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[Sense](testCreatureSenseJson) mustBe JsSuccess(testCreatureSense)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "value"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureSenseJson, path)
          Json.fromJson[Sense](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The DamageIntake case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[DamageIntake](testCreatureDamageIntakeJson) mustBe JsSuccess(testCreatureDamageIntake)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "value"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureDamageIntakeJson, path)
          Json.fromJson[DamageIntake](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The Trait case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[Trait](testCreatureTraitJson) mustBe JsSuccess(testCreatureTrait)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "description"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureTraitJson, path)
          Json.fromJson[Trait](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The Action case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[Action](testCreatureActionJson) mustBe JsSuccess(testCreatureAction)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "description"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureActionJson, path)
          Json.fromJson[Action](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }

  "The LegendaryAction case class" must {
    "read from json successfully" when {
      "the json is complete" in {
        Json.fromJson[LegendaryAction](testCreatureLegendaryActionJson) mustBe JsSuccess(testCreatureLegendaryAction)
      }
    }

    "fail to read from json" when {
      List(
        JsPath \ "name",
        JsPath \ "description"
      ) foreach { path =>
        s"$path is missing from the json" in {
          val json = prunePathFromJson(testCreatureLegendaryActionJson, path)
          Json.fromJson[LegendaryAction](json) mustBe JsError(path, "error.path.missing")
        }
      }
    }
  }


}
