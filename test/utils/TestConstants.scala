package utils

import models.Campaign
import play.api.libs.json.{JsObject, Json, OFormat}

trait TestConstants extends BaseRepositoryTestConstants with CampaignConstants {

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
