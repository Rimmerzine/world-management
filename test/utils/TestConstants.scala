package utils

import models.Campaign
import play.api.libs.json.{JsObject, Json}

trait TestConstants extends BaseRepositoryTestConstants with CampaignConstants {

  val emptyJson: JsObject = Json.obj()

}

trait BaseRepositoryTestConstants {

  val document: JsObject = Json.obj("testKey" -> "testValue")
  val document2: JsObject = Json.obj("testKey" -> "testValue2")

}

trait CampaignConstants {

  val testCampaignName: String = "testCampaignName"
  val testCampaignDescription: String = "testCampaignDescription"

  val testCampaign: Campaign = Campaign(testCampaignName, Some(testCampaignDescription))
  val testCampaignMinimal: Campaign = Campaign(testCampaignName, None
  )
  def testCampaigns(count: Int): List[Campaign] = {
    for {
      num <- (0 to count).toList
    } yield {
      Campaign(testCampaignName + num, Some(testCampaignDescription + num))
    }
  }

  val testCampaignJson: JsObject = Json.obj("name" -> testCampaignName, "description" -> testCampaignDescription)
  val testCampaignMinimalJson: JsObject = Json.obj("name" -> testCampaignName)

}
