package repositories

import helpers.RepositorySpec
import play.modules.reactivemongo.ReactiveMongoApi
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class BaseRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-campaigns"

  class Setup(documents: Tester*) extends BaseRepository {
    val reactiveApi: ReactiveMongoApi = testReactiveApi
    val databaseName: String = testDatabaseName
    val collectionName: String = testCollectionName

    insertMany(documents.toList)
  }

  "findOne" must {
    "return a record" when {
      "there is only one in the repository" in new Setup(testerFull) {
        await(findOne[Tester](emptyJson)) mustBe Some(testerFull)
      }
      "there are multiple in the repository" in new Setup(testerFull, testerFull2) {
        await(findOne[Tester](emptyJson)) mustBe Some(testerFull)
      }
    }

    "return the correct record when there is a selector" in new Setup(testerFull, testerFull2) {
      await(findOne[Tester](testerFull2Json)) mustBe Some(testerFull2)
    }

    "return no record" when {
      "there is no record in the repository" in new Setup {
        await(findOne[Tester](emptyJson)) mustBe None
      }
      "there are records in the repository but they do not match the selector" in new Setup(testerFull) {
        await(findOne[Tester](testerFull2Json)) mustBe None
      }
    }
  }

  "find" must {
    "return a list of records" when {
      "there is just one record" in new Setup(testerFull) {
        await(find[Tester](testerFullJson)) mustBe List(testerFull)
      }
      "there are multiple records" in new Setup(testerFull, testerFull2) {
        await(find[Tester](emptyJson)) mustBe List(testerFull, testerFull2)
      }
    }
    "return no records" when {
      "there are no records in the repository" in new Setup {
        await(find[Tester](emptyJson)) mustBe List.empty[Tester]
      }
      "there are no matching records in the repository" in new Setup(testerFull) {
        await(find[Tester](testerFull2Json)) mustBe List.empty[Tester]
      }
    }
  }

  "insert" must {
    "return what was inserted" when {
      "the insert was successful" in new Setup {
        await(insert[Tester](testerFull)) mustBe testerFull
      }
    }
  }

  "findAndUpdate" must {
    "update a single document with the new json and return the new document" when {
      "the document to update is in the database" in new Setup(testerFull) {
        await(findAndUpdate[Tester](testerFullJson, testerMinJson)) mustBe Some(testerMin)
        findAll[Tester] mustBe List(testerMin)
      }
      "there are multiple documents matching the search criteria" in new Setup(testerFull, testerFull) {
        await(findAndUpdate[Tester](testerFullJson, testerMinJson)) mustBe Some(testerMin)
        findAll[Tester] mustBe List(testerMin, testerFull)
      }
    }
    "return None and not update any documents" when {
      "there are no documents in the database" in new Setup {
        await(findAndUpdate[Tester](testerFullJson, testerMinJson)) mustBe None
        findAll[Tester] mustBe List.empty[Tester]
      }
      "there are no documents matching the selector" in new Setup(testerFull) {
        await(findAndUpdate[Tester](testerFull2Json, testerFullJson)) mustBe None
        findAll[Tester] mustBe List(testerFull)
      }
    }
  }

}
