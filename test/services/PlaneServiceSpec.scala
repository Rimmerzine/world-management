package services

import helpers.UnitSpec
import models.Plane
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import repositories.PlaneRepository
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PlaneServiceSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockPlaneRepository: PlaneRepository = mock[PlaneRepository]

    val service: PlaneService = new PlaneService {
      val planeRepository: PlaneRepository = mockPlaneRepository
    }
  }

  "retrievePlanes" must {
    "return back the planes from the repository" in new Setup {
      val planesList: List[Plane] = testPlanes(5)
      when(mockPlaneRepository.retrievePlanes(matches(planesList.head.campaignId))(any())) thenReturn Future.successful(planesList)
      await(service.retrievePlanes(planesList.head.campaignId)) mustBe planesList
    }
  }

  "retrieveSinglePlane" must {
    "return back the plane from the repository" in new Setup {
      when(mockPlaneRepository.retrieveSinglePlane(matches(testPlane.planeId))(any())) thenReturn Future.successful(Some(testPlane))
      await(service.retrieveSinglePlane(testPlane.planeId)) mustBe Some(testPlane)
    }
  }

  "createPlane" must {
    "return what is returned from the repository" in new Setup {
      when(mockPlaneRepository.insertPlane(matches(testPlane))(any())) thenReturn Future.successful(testPlane)
      await(service.createPlane(testPlane)) mustBe testPlane
    }
  }

  "updatePlane" must {
    "return what is returned from the repository" in new Setup {
      when(mockPlaneRepository.updatePlane(matches(testPlane))(any())) thenReturn Future.successful(Some(testPlane))
      await(service.updatePlane(testPlane)) mustBe Some(testPlane)
    }
  }

  "removePlane" must {
    "return what is returned from the repository" in new Setup {
      when(mockPlaneRepository.removePlane(matches(testPlane.planeId))(any())) thenReturn Future.successful(Some(testPlane))
      await(service.removePlane(testPlane.planeId)) mustBe Some(testPlane)
    }
  }

}
