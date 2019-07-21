package models

import play.api.libs.json.{Json, OFormat}

case class Plane(campaignId: String, planeId: String, name: String, description: Option[String], alignment: String)

object Plane {

  implicit val format: OFormat[Plane] = Json.format[Plane]

}
