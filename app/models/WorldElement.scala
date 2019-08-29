package models

import play.api.libs.json._

case class Campaign(
                     elementType: String,
                     id: String,
                     name: String,
                     description: Option[String],
                     content: List[WorldElement]
                   ) extends WorldElement

object Campaign {
  val reads: Reads[Campaign] = Json.reads[Campaign]
  val writes: OWrites[Campaign] = Json.writes[Campaign]
}

case class Plane(
                  elementType: String,
                  id: String,
                  name: String,
                  description: Option[String],
                  content: List[WorldElement],
                  alignment: String
                ) extends WorldElement

object Plane {
  val reads: Reads[Plane] = Json.reads[Plane]
  val writes: OWrites[Plane] = Json.writes[Plane]
}

case class Land(
                 elementType: String,
                 id: String,
                 name: String,
                 description: Option[String],
                 content: List[WorldElement]
               ) extends WorldElement

object Land {
  val reads: Reads[Land] = Json.reads[Land]
  val writes: OWrites[Land] = Json.writes[Land]
}

sealed trait WorldElement {
  val elementType: String
  val id: String
  val name: String
  val description: Option[String]
  val content: List[WorldElement]
}

object WorldElement {

  val jsonToWorldElement: JsValue => JsResult[WorldElement] = { json =>
    (json \ "elementType").validateOpt[String] flatMap {
      case Some("campaign") => Json.fromJson(json)(Campaign.reads)
      case Some("plane") => Json.fromJson(json)(Plane.reads)
      case Some("land") => Json.fromJson(json)(Land.reads)
      case _ => JsError(JsPath \ "elementType", "error.invalid")
    }
  }

  val worldElementToJson: WorldElement => JsObject = {
    case campaign: Campaign => Json.toJsObject(campaign)(Campaign.writes)
    case plane: Plane => Json.toJsObject(plane)(Plane.writes)
    case land: Land => Json.toJsObject(land)(Land.writes)
  }

  val reads: Reads[WorldElement] = Reads[WorldElement](jsonToWorldElement)
  val writes: OWrites[WorldElement] = OWrites[WorldElement](worldElementToJson)

  implicit val format: OFormat[WorldElement] = OFormat(reads, writes)

}