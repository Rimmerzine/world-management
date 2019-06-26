package models

import play.api.libs.json.{Json, OFormat}

case class Campaign(id: String, name: String, description: Option[String])

object Campaign {
  implicit val format: OFormat[Campaign] = Json.format[Campaign]
}
