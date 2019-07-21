package services

import javax.inject.Inject
import models.Land
import repositories.LandRepository

import scala.concurrent.{ExecutionContext, Future}

class LandServiceImpl @Inject()(val landRepository: LandRepository) extends LandService

trait LandService {

  protected val landRepository: LandRepository

  def retrieveLands(planeId: String)(implicit ec: ExecutionContext): Future[List[Land]] = landRepository.retrieveLands(planeId)

  def retrieveSingleLand(landId: String)(implicit ec: ExecutionContext): Future[Option[Land]] = {
    landRepository.retrieveSingleLand(landId)
  }

  def createLand(land: Land)(implicit ec: ExecutionContext): Future[Land] = landRepository.insertLand(land)

  def updateLand(land: Land)(implicit ec: ExecutionContext): Future[Option[Land]] = landRepository.updateLand(land)

  def removeLand(landId: String)(implicit ec: ExecutionContext): Future[Option[Land]] = landRepository.removeLand(landId)

}
