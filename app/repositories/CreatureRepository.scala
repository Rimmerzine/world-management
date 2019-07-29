package repositories

import javax.inject.Inject
import models.Creature
import play.api.libs.json.{JsObject, JsPath, Json}
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}

class CreatureRepositoryImpl @Inject()(val reactiveApi: ReactiveMongoApi) extends CreatureRepository {
  val databaseName: String = "world-management"
  val collectionName: String = "creatures"
}

trait CreatureRepository extends BaseRepository {

  def retrieveCreatures(challengeRating: Option[Double], nameStart: Option[Char])(implicit ec: ExecutionContext): Future[List[Creature]] = {
    val crFilter: JsObject = Json.toJsObject(challengeRating)(writeNullable(JsPath \ "challengeRating"))
    val nameFilter: JsObject = Json.toJsObject(nameStart.map(s"(?i)" + _ + "(?i)(.*)"))(writeNullable(JsPath \ "name" \ "$regex"))
    find[Creature](crFilter ++ nameFilter)
  }

  def retrieveSingleCreature(creatureId: String)(implicit ec: ExecutionContext): Future[Option[Creature]] = {
    val selector: JsObject = Json.obj("id" -> creatureId)
    findOne[Creature](selector)
  }

  def insertCreature(creature: Creature)(implicit ec: ExecutionContext): Future[Creature] = {
    insert(creature)
  }

  def updateCreature(creature: Creature)(implicit ec: ExecutionContext): Future[Option[Creature]] = {
    val selector: JsObject = Json.obj("id" -> creature.id)
    findAndUpdate[Creature](selector, Json.toJsObject(creature))
  }

  def removeCreature(creatureId: String)(implicit ec: ExecutionContext): Future[Option[Creature]] = {
    val selector: JsObject = Json.obj("id" -> creatureId)
    findAndRemove[Creature](selector)
  }

}
