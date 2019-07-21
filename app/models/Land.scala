package models

import play.api.libs.json.{Json, OFormat}

case class Land(planeId: String, landId: String, name: String, description: Option[String])

object Land {

  implicit val format: OFormat[Land] = Json.format[Land]

}
