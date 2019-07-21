package utils

import models.{Campaign, Land, Plane}
import play.api.libs.json.{JsObject, Json, OFormat}

trait TestConstants extends BaseRepositoryTestConstants with CampaignConstants with PlaneConstants with LandConstants {

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
