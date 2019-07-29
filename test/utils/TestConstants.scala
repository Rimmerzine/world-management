package utils

import models._
import play.api.libs.json.{JsObject, Json, OFormat}

trait TestConstants extends BaseRepositoryTestConstants with CampaignConstants with PlaneConstants with LandConstants with CreatureConstants {

  val emptyJson: JsObject = Json.obj()

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

trait CampaignConstants {

  val testCampaignId: String = "testCampaignId"
  val testCampaignName: String = "testCampaignName"
  val testCampaignDescription: String = "testCampaignDescription"

  val testCampaign: Campaign = Campaign(testCampaignId + "Full", testCampaignName, Some(testCampaignDescription))
  val testCampaignMinimal: Campaign = Campaign(testCampaignId + "Min", testCampaignName, None)

  def testCampaigns(count: Int): List[Campaign] = {
    for {
      num <- (0 to count).toList
    } yield {
      Campaign(testCampaignId + num, testCampaignName + num, Some(testCampaignDescription + num))
    }
  }

  val testCampaignJson: JsObject = Json.obj("id" -> (testCampaignId + "Full"), "name" -> testCampaignName, "description" -> testCampaignDescription)
  val testCampaignMinimalJson: JsObject = Json.obj("id" -> (testCampaignId + "Min"), "name" -> testCampaignName)

}

trait PlaneConstants {

  val testCampaignId: String
  val testPlaneId: String = "testPlaneId"
  val testPlaneName: String = "testPlaneName"
  val testPlaneDescription: String = "testPlaneDescription"
  val testPlaneAlignment: String = "testPlaneAlignment"

  val testPlane: Plane = Plane(testCampaignId, testPlaneId + "Full", testPlaneName, Some(testPlaneDescription), testPlaneAlignment)
  val testPlaneMinimal: Plane = Plane(testCampaignId, testPlaneId + "Min", testPlaneName, None, testPlaneAlignment)

  def testPlanes(count: Int): List[Plane] = {
    for {
      num <- (0 to count).toList
    } yield {
      Plane(testCampaignId, testPlaneId + num, testPlaneName + num, Some(testPlaneDescription + num), testPlaneAlignment + num)
    }
  }

  val testPlaneJson: JsObject = Json.obj(
    "campaignId" -> testCampaignId,
    "planeId" -> (testPlaneId + "Full"),
    "name" -> testPlaneName,
    "description" -> testPlaneDescription,
    "alignment" -> testPlaneAlignment
  )

  val testPlaneMinimalJson: JsObject = Json.obj(
    "campaignId" -> testCampaignId,
    "planeId" -> (testPlaneId + "Min"),
    "name" -> testPlaneName,
    "alignment" -> testPlaneAlignment
  )

}

trait LandConstants {

  val testPlaneId: String
  val testLandId: String = "testLandId"
  val testLandName: String = "testLandName"
  val testLandDescription: String = "testLandDescription"

  val testLand: Land = Land(testPlaneId, testLandId + "Full", testLandName, Some(testLandDescription))
  val testLandMinimal: Land = Land(testPlaneId, testLandId + "Min", testLandName, None)

  def testLands(count: Int): List[Land] = {
    for {
      num <- (0 to count).toList
    } yield {
      Land(testPlaneId, testLandId + num, testLandName + num, Some(testLandDescription + num))
    }
  }

  val testLandJson: JsObject = Json.obj(
    "planeId" -> testPlaneId,
    "landId" -> (testLandId + "Full"),
    "name" -> testLandName,
    "description" -> testLandDescription
  )

  val testLandMinimalJson: JsObject = Json.obj(
    "planeId" -> testPlaneId,
    "landId" -> (testLandId + "Min"),
    "name" -> testLandName
  )

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
