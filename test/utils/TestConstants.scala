package utils

import models._
import play.api.libs.json.{JsObject, JsPath, Json, OFormat}

trait TestConstants extends BaseRepositoryTestConstants with CreatureConstants with WorldElementConstants {

  val emptyJson: JsObject = Json.obj()

}

trait WorldElementConstants {

  val campaignId: String = "testCampaignId"
  val campaignName: String = "testCampaignName"
  val campaignDescription: String = "testCampaignDescription"

  val campaign: Campaign = Campaign("campaign", campaignId, campaignName, Some(campaignDescription), List.empty[WorldElement])
  val campaignJson: JsObject = Json.obj(
    "elementType" -> "campaign",
    "id" -> campaignId,
    "name" -> campaignName,
    "description" -> campaignDescription,
    "content" -> Json.arr()
  )

  val campaignMinimal: Campaign = campaign.copy(description = None)
  val campaignMinimalJson: JsObject = campaignJson - "description"

  val planeId: String = "testPlaneId"
  val planeName: String = "testPlaneName"
  val planeDescription: String = "testPlaneDescription"
  val planeAlignment: String = "unaligned"

  val plane: Plane = Plane("plane", planeId, planeName, Some(planeDescription), List.empty[WorldElement], planeAlignment)
  val planeJson: JsObject = Json.obj(
    "elementType" -> "plane",
    "id" -> planeId,
    "name" -> planeName,
    "description" -> planeDescription,
    "content" -> Json.arr(),
    "alignment" -> planeAlignment
  )

  val planeMinimal: Plane = plane.copy(description = None)
  val planeMinimalJson: JsObject = planeJson - "description"



  val landId: String = "testLandId"
  val landName: String = "testLandName"
  val landDescription: String = "testLandDescription"

  val land: Land = Land("land", landId, landName, Some(landDescription), List.empty[WorldElement])
  val landJson: JsObject = Json.obj(
    "elementType" -> "land",
    "id" -> landId,
    "name" -> landName,
    "description" -> landDescription,
    "content" -> Json.arr()
  )

  val landMinimal: Land = land.copy(description = None)
  val landMinimalJson: JsObject = landJson - "description"

}

trait BaseRepositoryTestConstants {

  case class Tester(one: String, two: Option[Int], three: Boolean = false)

  object Tester {
    implicit val format: OFormat[Tester] = Json.format[Tester]
  }

  val testerFull: Tester = Tester("testOne", Some(2), three = true)
  val testerFull2: Tester = Tester("testOne", Some(2))
  val testerMin: Tester = Tester("testOne", None)

  val testerFullJson: JsObject = Json.obj("one" -> "testOne", "two" -> 2, "three" -> true)
  val testerFull2Json: JsObject = Json.obj("one" -> "testOne", "two" -> 2, "three" -> false)
  val testerMinJson: JsObject = Json.obj("one" -> "testOne", "three" -> false)

}

trait CreatureConstants {

  val testCreatureId: String = "testCreatureId"
  val testCreatureName: String = "testCreatureName"
  val testCreatureDescription: String = "testCreatureDescription"
  val testCreatureSize: String = "testCreatureSize"
  val testCreatureAlignment: String = "testCreatureAlignment"
  val testCreatureArmourClass: Int = 20
  val testCreatureHitPoints: String = "18d8+54"
  val testCreatureType: String = "testCreatureType"
  val testCreatureChallengeRating: Double = 20
  val testCreatureTypeTag: String = "testCreatureTypeTag"

  val testMovementSpeedName: String = "testMovementSpeedName"
  val testMovementSpeedValue: Int = 30
  val testCreatureMovementSpeed: MovementSpeed = MovementSpeed(testMovementSpeedName, testMovementSpeedValue)

  val testCreatureMovementSpeedJson: JsObject = Json.obj(
    "name" -> testMovementSpeedName,
    "value" -> testMovementSpeedValue
  )

  val testAbilityScoreName: String = "testAbilityScoreName"
  val testAbilityScoreValue: Int = 20
  val testAbilityScoreProficient: Boolean = true
  val testCreatureAbilityScore: AbilityScore = AbilityScore(testAbilityScoreName, testAbilityScoreValue, testAbilityScoreProficient)

  val testCreatureAbilityScoreJson: JsObject = Json.obj(
    "name" -> testAbilityScoreName,
    "value" -> testAbilityScoreValue,
    "proficient" -> testAbilityScoreProficient
  )

  val testSkillProficiencyName: String = "testSkillProficiencyName"
  val testSkillProficiencyValue: String = "testSkillProficiencyValue"
  val testCreatureSkillProficiency: SkillProficiency = SkillProficiency(testSkillProficiencyName, testSkillProficiencyValue)

  val testCreatureSkillProficiencyJson: JsObject = Json.obj(
    "name" -> testSkillProficiencyName,
    "value" -> testSkillProficiencyValue
  )

  val testDamageIntakeName: String = "testDamageIntakeName"
  val testDamageIntakeValue: String = "testDamageIntakeValue"
  val testCreatureDamageIntake: DamageIntake = DamageIntake(testDamageIntakeName, testDamageIntakeValue)

  val testCreatureDamageIntakeJson: JsObject = Json.obj(
    "name" -> testDamageIntakeName,
    "value" -> testDamageIntakeValue
  )

  val testCreatureConditionImmunity: String = "testCreatureConditionImmunity"

  val testSenseName: String = "testSenseName"
  val testSenseValue: Int = 30
  val testCreatureSense: Sense = Sense(testSenseName, testSenseValue)

  val testCreatureSenseJson: JsObject = Json.obj(
    "name" -> testSenseName,
    "value" -> testSenseValue
  )

  val testCreatureLanguage: String = "testCreatureLanguage"

  val testTraitName: String = "testTraitName"
  val testTraitDescription: String = "testTraitDescription"
  val testCreatureTrait: Trait = Trait(testTraitName, testTraitDescription)

  val testCreatureTraitJson: JsObject = Json.obj(
    "name" -> testTraitName,
    "description" -> testTraitDescription
  )

  val testActionName: String = "testActionName"
  val testActionDescription: String = "testActionDescription"
  val testCreatureAction: Action = Action(testActionName, testActionDescription)

  val testCreatureActionJson: JsObject = Json.obj(
    "name" -> testActionName,
    "description" -> testActionDescription
  )

  val testLegendaryActionName: String = "testLegendaryActionName"
  val testLegendaryActionDescription: String = "testLegendaryActionDescription"
  val testCreatureLegendaryAction: LegendaryAction = LegendaryAction(testLegendaryActionName, testLegendaryActionDescription)

  val testCreatureLegendaryActionJson: JsObject = Json.obj(
    "name" -> testLegendaryActionName,
    "description" -> testLegendaryActionDescription
  )

  val testCreature: Creature = Creature(
    id = testCreatureId,
    name = testCreatureName,
    description = Some(testCreatureDescription),
    size = testCreatureSize,
    alignment = testCreatureAlignment,
    armourClass = testCreatureArmourClass,
    hitPoints = testCreatureHitPoints,
    creatureType = testCreatureType,
    challengeRating = testCreatureChallengeRating,
    typeTags = List(testCreatureTypeTag),
    movementSpeeds = List(testCreatureMovementSpeed),
    abilityScores = List(testCreatureAbilityScore),
    skillProficiencies = List(testCreatureSkillProficiency),
    damageIntakes = List(testCreatureDamageIntake),
    conditionImmunities = List(testCreatureConditionImmunity),
    senses = List(testCreatureSense),
    languages = List(testCreatureLanguage),
    creatureTraits = List(testCreatureTrait),
    actions = List(testCreatureAction),
    legendaryActions = List(testCreatureLegendaryAction)
  )

  val testCreatureJson: JsObject = Json.obj(
    "id" -> testCreatureId,
    "name" -> testCreatureName,
    "description" -> testCreatureDescription,
    "size" -> testCreatureSize,
    "alignment" -> testCreatureAlignment,
    "armourClass" -> testCreatureArmourClass,
    "hitPoints" -> testCreatureHitPoints,
    "creatureType" -> testCreatureType,
    "challengeRating" -> testCreatureChallengeRating,
    "typeTags" -> Json.arr(
      testCreatureTypeTag
    ),
    "movementSpeeds" -> Json.arr(
      Json.obj(
        "name" -> testMovementSpeedName,
        "value" -> testMovementSpeedValue
      )
    ),
    "abilityScores" -> Json.arr(
      Json.obj(
        "name" -> testAbilityScoreName,
        "value" -> testAbilityScoreValue,
        "proficient" -> testAbilityScoreProficient
      )
    ),
    "skillProficiencies" -> Json.arr(
      Json.obj(
        "name" -> testSkillProficiencyName,
        "value" -> testSkillProficiencyValue
      )
    ),
    "damageIntakes" -> Json.arr(
      Json.obj(
        "name" -> testDamageIntakeName,
        "value" -> testDamageIntakeValue
      )
    ),
    "conditionImmunities" -> Json.arr(
      testCreatureConditionImmunity
    ),
    "senses" -> Json.arr(
      Json.obj(
        "name" -> testSenseName,
        "value" -> testSenseValue
      )
    ),
    "languages" -> Json.arr(
      testCreatureLanguage
    ),
    "creatureTraits" -> Json.arr(
      Json.obj(
        "name" -> testTraitName,
        "description" -> testTraitDescription
      )
    ),
    "actions" -> Json.arr(
      Json.obj(
        "name" -> testActionName,
        "description" -> testActionDescription
      )
    ),
    "legendaryActions" -> Json.arr(
      Json.obj(
        "name" -> testLegendaryActionName,
        "description" -> testLegendaryActionDescription
      )
    )
  )

  val testCreatureMinimal: Creature = Creature(
    id = testCreatureId,
    name = testCreatureName,
    description = None,
    size = testCreatureSize,
    alignment = testCreatureAlignment,
    armourClass = testCreatureArmourClass,
    hitPoints = testCreatureHitPoints,
    creatureType = testCreatureType,
    challengeRating = testCreatureChallengeRating,
    typeTags = List.empty[String],
    movementSpeeds = List.empty[MovementSpeed],
    abilityScores = List.empty[AbilityScore],
    skillProficiencies = List.empty[SkillProficiency],
    damageIntakes = List.empty[DamageIntake],
    conditionImmunities = List.empty[String],
    senses = List.empty[Sense],
    languages = List.empty[String],
    creatureTraits = List.empty[Trait],
    actions = List.empty[Action],
    legendaryActions = List.empty[LegendaryAction]
  )

  val testCreatureMinimalJson: JsObject = Json.obj(
    "id" -> testCreatureId,
    "name" -> testCreatureName,
    "size" -> testCreatureSize,
    "alignment" -> testCreatureAlignment,
    "armourClass" -> testCreatureArmourClass,
    "hitPoints" -> testCreatureHitPoints,
    "creatureType" -> testCreatureType,
    "challengeRating" -> testCreatureChallengeRating,
    "typeTags" -> Json.arr(),
    "movementSpeeds" -> Json.arr(),
    "abilityScores" -> Json.arr(),
    "skillProficiencies" -> Json.arr(),
    "damageIntakes" -> Json.arr(),
    "conditionImmunities" -> Json.arr(),
    "senses" -> Json.arr(),
    "languages" -> Json.arr(),
    "creatureTraits" -> Json.arr(),
    "actions" -> Json.arr(),
    "legendaryActions" -> Json.arr()
  )

}
