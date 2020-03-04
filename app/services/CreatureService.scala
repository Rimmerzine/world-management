package services

import javax.inject.Inject
import models.Creature
import repositories.CreatureRepository

import scala.concurrent.{ExecutionContext, Future}

class CreatureServiceImpl @Inject()(val creatureRepository: CreatureRepository) extends CreatureService

trait CreatureService {

  protected val creatureRepository: CreatureRepository

  def retrieveCreatures(implicit ec: ExecutionContext): Future[List[Creature]] = {
    creatureRepository.retrieveCreatures
  }

  def retrieveSingleCreature(creatureId: String)(implicit ec: ExecutionContext): Future[Option[Creature]] = {
    creatureRepository.retrieveSingleCreature(creatureId)
  }

  def createCreature(creature: Creature)(implicit ec: ExecutionContext): Future[Creature] = {
    creatureRepository.insertCreature(creature)
  }

  def updateCreature(creature: Creature)(implicit ec: ExecutionContext): Future[Option[Creature]] = {
    creatureRepository.updateCreature(creature)
  }

  def removeCreature(creatureId: String)(implicit ec: ExecutionContext): Future[Option[Creature]] = {
    creatureRepository.removeCreature(creatureId)
  }

}
