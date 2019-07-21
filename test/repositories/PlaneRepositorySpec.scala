package repositories

import helpers.RepositorySpec
import models.Plane
import play.modules.reactivemongo.ReactiveMongoApi
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class PlaneRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-planes"

  class Setup(repoPlanes: Plane*) {
    val repository: PlaneRepository = new PlaneRepository {
      val reactiveApi: ReactiveMongoApi = testReactiveApi
      val databaseName: String = testDatabaseName
      val collectionName: String = testCollectionName
    }

    insertMany(repoPlanes.toList)
  }

  "retrievePlanes" must {
    "return planes from the database with the specified campaign id" when {
      "there is one plane in the database with that campaign id" in new Setup(testPlane) {
        await(repository.retrievePlanes(testCampaignId)) mustBe List(testPlane)
      }
      "there are multiple planes in the database for the campaign id" in new Setup(testPlanes(5): _*) {
        await(repository.retrievePlanes(testCampaignId)) mustBe testPlanes(5)
      }
    }
    "return nothing from the database" when {
      "there are no campaigns stored in the database" in new Setup {
        await(repository.retrievePlanes(testCampaignId)) mustBe List.empty[Plane]
      }
    }
  }

  "retrieveSinglePlane" must {
    "return a single plane from the database" when {
      "there is one plane in the database with the plane id" in new Setup(testPlane) {
        await(repository.retrieveSinglePlane(testPlane.planeId)) mustBe Some(testPlane)
      }
      "there are multiple planes in the database" in new Setup(testPlaneMinimal, testPlane) {
        await(repository.retrieveSinglePlane(testPlane.planeId)) mustBe Some(testPlane)
      }
    }
    "return nothing from the database" when {
      "there are no planes in the database" in new Setup {
        await(repository.retrieveSinglePlane(testPlane.planeId)) mustBe None
      }
      "the selected plane is not in the database" in new Setup(testPlaneMinimal) {
        await(repository.retrieveSinglePlane(testPlane.planeId)) mustBe None
      }
    }
  }

  "insertPlane" must {
    "insert a full plane into the database and return back the plane inserted" when {
      "the database does not contain any planes" in new Setup {
        await(repository.insertPlane(testPlane)) mustBe testPlane
        findAll[Plane] mustBe List(testPlane)
      }
      "the database already contains a planes" in new Setup(testPlane) {
        await(repository.insertPlane(testPlane)) mustBe testPlane
        findAll[Plane] mustBe List(testPlane, testPlane)
      }
    }
  }

  "updatePlane" must {
    "update the plane on the database" in new Setup(testPlane) {
      val updatedPlane: Plane = testPlane.copy(name = "testUpdatedName")
      await(repository.updatePlane(updatedPlane)) mustBe Some(updatedPlane)
    }
    "return none and not update any planes" when {
      "there are no planes" in new Setup {
        await(repository.updatePlane(testPlane)) mustBe None
      }
      "the planes to update is not in the database" in new Setup(testPlane) {
        await(repository.updatePlane(testPlaneMinimal)) mustBe None
      }
    }
  }

  "removePlane" must {
    "remove the plane from the database" when {
      "it exists in the database" in new Setup(testPlane) {
        await(repository.removePlane(testPlane.planeId)) mustBe Some(testPlane)
      }
    }
    "return none" when {
      "there are no records in the database" in new Setup {
        await(repository.removePlane(testPlane.planeId)) mustBe None
      }
      "the plane doesn't exist in the database" in new Setup(testPlane) {
        await(repository.removePlane(testPlaneMinimal.planeId))
      }
    }
  }

}
