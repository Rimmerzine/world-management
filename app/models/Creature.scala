package models

import play.api.libs.json._

case class Creature(
                     id: String,
                     name: String,
                     description: Option[String],
                     size: String,
                     alignment: String,
                     armourClass: Int,
                     hitPoints: String,
                     creatureType: String,
                     challengeRating: Double,
                     typeTags: List[String],
                     movementSpeeds: List[MovementSpeed],
                     abilityScores: List[AbilityScore],
                     skillProficiencies: List[SkillProficiency],
                     damageIntakes: List[DamageIntake],
                     conditionImmunities: List[String],
                     senses: List[Sense],
                     languages: List[String],
                     creatureTraits: List[Trait],
                     actions: List[Action],
                     legendaryActions: List[LegendaryAction]
                   )

case class MovementSpeed(name: String, value: Int)

case class AbilityScore(name: String, value: Int, proficient: Boolean)

case class SkillProficiency(name: String, value: String)

case class Sense(name: String, value: Int)

case class DamageIntake(name: String, value: String)

case class Trait(name: String, description: String)

case class Action(name: String, description: String)

case class LegendaryAction(name: String, description: String)

object Creature {
  implicit val format: OFormat[Creature] = Json.format[Creature]
}

object MovementSpeed {
  implicit lazy val format: OFormat[MovementSpeed] = Json.format[MovementSpeed]
}

object AbilityScore {
  implicit lazy val format: OFormat[AbilityScore] = Json.format[AbilityScore]
}

object SkillProficiency {
  implicit lazy val format: OFormat[SkillProficiency] = Json.format[SkillProficiency]
}

object Sense {
  implicit lazy val format: OFormat[Sense] = Json.format[Sense]
}

object DamageIntake {
  implicit lazy val format: OFormat[DamageIntake] = Json.format[DamageIntake]
}

object Trait {
  implicit lazy val format: OFormat[Trait] = Json.format[Trait]
}

object Action {
  implicit lazy val format: OFormat[Action] = Json.format[Action]
}

object LegendaryAction {
  implicit lazy val format: OFormat[LegendaryAction] = Json.format[LegendaryAction]
}
