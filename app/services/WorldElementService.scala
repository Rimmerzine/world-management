package services

import javax.inject.Inject
import models.WorldElement
import repositories.WorldElementRepository

import scala.concurrent.{ExecutionContext, Future}

class WorldElementServiceImpl @Inject()(val worldElementRepository: WorldElementRepository) extends WorldElementService

trait WorldElementService {

  protected val worldElementRepository: WorldElementRepository

  def retrieveWorldElements(implicit ec: ExecutionContext): Future[List[WorldElement]] = {
    worldElementRepository.retrieveWorldElements
  }

  def retrieveSingleWorldElement(campaignId: String)(implicit ec: ExecutionContext): Future[Option[WorldElement]] = {
    worldElementRepository.retrieveSingleWorldElement(campaignId)
  }

  def createWorldElement(campaign: WorldElement)(implicit ec: ExecutionContext): Future[WorldElement] = {
    worldElementRepository.insertWorldElement(campaign)
  }

  def updateWorldElement(campaign: WorldElement)(implicit ec: ExecutionContext): Future[Option[WorldElement]] = {
    worldElementRepository.updateWorldElement(campaign)
  }

  def removeWorldElement(campaignId: String)(implicit ec: ExecutionContext): Future[Option[WorldElement]] = {
    worldElementRepository.removeWorldElement(campaignId)
  }

}
