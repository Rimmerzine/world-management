package repositories

import helpers.RepositorySpec
import play.api.libs.json.{JsNull, JsObject, JsValue, Json}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.ImplicitBSONHandlers._
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global

class BaseRepositorySpec extends RepositorySpec with TestConstants {

  val testCollectionName: String = "test-campaigns"

  class Setup extends BaseRepository {
    val reactiveApi: ReactiveMongoApi = testReactiveApi
    val databaseName: String = testDatabaseName
    val collectionName: String = testCollectionName
  }

  "findOne" should {
    "return a record" when {
      "there is only one in the repository" in new Setup {
        insertOne[JsObject](document)
        await(findOne[JsObject](emptyJson)) mustBe Some(document)
      }
      "there are multiple in the repository" in new Setup {
        insertMany[JsObject](List(document, document2))
        await(findOne[JsObject](emptyJson)) mustBe Some(document)
      }
    }

    "return the correct record when there is a selector" in new Setup {
      insertMany[JsObject](List(document, document2))
      await(findOne[JsObject](document2)) mustBe Some(document2)
    }

    "return no record" when {
      "there is no record in the repository" in new Setup {
        await(findOne[JsObject](emptyJson)) mustBe None
      }
      "there are records in the repository but they do not match the selector" in new Setup {
        insert[JsObject](document)
        await(findOne[JsObject](document2)) mustBe None
      }
    }
  }

  "find" should {
    "return a list of records" when {
      "there is just one record" in new Setup {
        insert[JsObject](document)
        await(find[JsObject](document)) mustBe List(document)
      }
      "there are multiple records" in new Setup {
        insertMany[JsObject](List(document, document2))
        await(find[JsObject](emptyJson)) mustBe List(document, document2)
      }
    }
    "return no records" when {
      "there are no records in the repository" in new Setup {
        await(find[JsObject](emptyJson)) mustBe List.empty[JsObject]
      }
      "there are no matching records in the repository" in new Setup {
        await(find[JsObject](document)) mustBe List.empty[JsObject]
      }
    }
  }

  "insert" should {
    "return what was inserted" when {
      "the insert was successful" in new Setup {
        await(insert[JsObject](document)) mustBe document
      }
    }
  }

}
