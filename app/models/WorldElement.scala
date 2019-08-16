package models

import play.api.libs.json.{JsPath, Json, OFormat, Reads}
import play.api.libs.json._

case class WMCampaign(
                     elementType: String,
                     id: String,
                     name: String,
                     description: Option[String],
                     content: List[WorldElement]
                   ) extends WorldElement

object WMCampaign {
  val reads: Reads[WMCampaign] = Json.reads[WMCampaign]
  val writes: OWrites[WMCampaign] = Json.writes[WMCampaign]
}

case class WMPlane(
                  elementType: String,
                  id: String,
                  name: String,
                  description: Option[String],
                  content: List[WorldElement],
                  alignment: String
                ) extends WorldElement

object WMPlane {
  val reads: Reads[WMPlane] = Json.reads[WMPlane]
  val writes: OWrites[WMPlane] = Json.writes[WMPlane]
}

case class WMLand(
                 elementType: String,
                 id: String,
                 name: String,
                 description: Option[String],
                 content: List[WorldElement]
               ) extends WorldElement

object WMLand {
  val reads: Reads[WMLand] = Json.reads[WMLand]
  val writes: OWrites[WMLand] = Json.writes[WMLand]
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
      case Some("campaign") => Json.fromJson(json)(WMCampaign.reads)
      case Some("plane") => Json.fromJson(json)(WMPlane.reads)
      case Some("land") => Json.fromJson(json)(WMLand.reads)
      case _ => JsError(JsPath \ "elementType", "error.invalid")
    }
  }

  val worldElementToJson: WorldElement => JsObject = {
    case campaign: WMCampaign => Json.toJsObject(campaign)(WMCampaign.writes)
    case plane: WMPlane => Json.toJsObject(plane)(WMPlane.writes)
    case land: WMLand => Json.toJsObject(land)(WMLand.writes)
  }

  val reads: Reads[WorldElement] = Reads[WorldElement](jsonToWorldElement)
  val writes: OWrites[WorldElement] = OWrites[WorldElement](worldElementToJson)

  implicit val format: OFormat[WorldElement] = OFormat(reads, writes)

}