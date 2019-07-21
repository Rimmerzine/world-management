package services

import javax.inject.Inject
import models.Plane
import repositories.PlaneRepository

import scala.concurrent.{ExecutionContext, Future}

class PlaneServiceImpl @Inject()(val planeRepository: PlaneRepository) extends PlaneService

trait PlaneService {

  protected val planeRepository: PlaneRepository

  def retrievePlanes(campaignId: String)(implicit ec: ExecutionContext): Future[List[Plane]] = planeRepository.retrievePlanes(campaignId)

  def retrieveSinglePlane(planeId: String)(implicit ec: ExecutionContext): Future[Option[Plane]] = {
    planeRepository.retrieveSinglePlane(planeId)
  }

  def createPlane(plane: Plane)(implicit ec: ExecutionContext): Future[Plane] = planeRepository.insertPlane(plane)

  def updatePlane(plane: Plane)(implicit ec: ExecutionContext): Future[Option[Plane]] = planeRepository.updatePlane(plane)

  def removePlane(planeId: String)(implicit ec: ExecutionContext): Future[Option[Plane]] = planeRepository.removePlane(planeId)

}
