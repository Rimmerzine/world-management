package repositories

import helpers.RepositorySpec
import models.Land
import play.modules.reactivemongo.ReactiveMongoApi
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class LandRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-lands"

  class Setup(repoLands: Land*) {
    val repository: LandRepository = new LandRepository {
      val reactiveApi: ReactiveMongoApi = testReactiveApi
      val databaseName: String = testDatabaseName
      val collectionName: String = testCollectionName
    }

    insertMany(repoLands.toList)
  }

  "retrieveLands" must {
    "return lands from the database" when {
      "there is one land in the database" in new Setup(testLand) {
        await(repository.retrieveLands(testLand.planeId)) mustBe List(testLand)
      }
      "there are multiple lands in the database" in new Setup(testLands(5): _*) {
        await(repository.retrieveLands(testPlaneId)) mustBe testLands(5)
      }
    }
    "return nothing from the database" when {
      "there are no lands stored in the database" in new Setup {
        await(repository.retrieveLands(testPlaneId)) mustBe List.empty[Land]
      }
    }
  }

  "retrieveSingleLand" must {
    "return a single land from the database" when {
      "there is one land in the database" in new Setup(testLand) {
        await(repository.retrieveSingleLand(testLand.landId)) mustBe Some(testLand)
      }
      "there are multiple lands in the database" in new Setup(testLandMinimal, testLand) {
        await(repository.retrieveSingleLand(testLand.landId)) mustBe Some(testLand)
      }
    }
    "return nothing from the database" when {
      "there are no lands in the database" in new Setup {
        await(repository.retrieveSingleLand(testLand.landId)) mustBe None
      }
      "the selected land is not in the database" in new Setup(testLandMinimal) {
        await(repository.retrieveSingleLand(testLand.landId)) mustBe None
      }
    }
  }

  "insertLand" must {
    "insert a full land into the database and return back the land inserted" when {
      "the database does not contain any lands" in new Setup {
        await(repository.insertLand(testLand)) mustBe testLand
        findAll[Land] mustBe List(testLand)
      }
      "the database already contains a land" in new Setup(testLand) {
        await(repository.insertLand(testLand)) mustBe testLand
        findAll[Land] mustBe List(testLand, testLand)
      }
    }
  }

  "updateLand" must {
    "update the land on the database" in new Setup(testLand) {
      val updatedLand: Land = testLand.copy(name = "testUpdatedName")
      await(repository.updateLand(updatedLand)) mustBe Some(updatedLand)
    }
    "return none and not update any land" when {
      "there are no lands" in new Setup {
        await(repository.updateLand(testLand)) mustBe None
      }
      "the land to update is not in the database" in new Setup(testLand) {
        await(repository.updateLand(testLandMinimal)) mustBe None
      }
    }
  }

  "removeLand" must {
    "remove the land from the database" when {
      "it exists in the database" in new Setup(testLand) {
        await(repository.removeLand(testLand.landId)) mustBe Some(testLand)
      }
    }
    "return none" when {
      "there are no records in the database" in new Setup {
        await(repository.removeLand(testLand.landId)) mustBe None
      }
      "the land doesn't exist in the database" in new Setup(testLand) {
        await(repository.removeLand(testLandMinimal.landId))
      }
    }
  }

}
