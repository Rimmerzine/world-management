package services

import helpers.UnitSpec
import models.Land
import org.mockito.ArgumentMatchers.{any, eq => matches}
import org.mockito.Mockito.when
import repositories.LandRepository
import utils.TestConstants

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LandServiceSpec extends UnitSpec with TestConstants {

  class Setup {
    val mockLandRepository: LandRepository = mock[LandRepository]

    val service: LandService = new LandService {
      val landRepository: LandRepository = mockLandRepository
    }
  }

  "retrieveLands" must {
    "return back the land from the repository" in new Setup {
      val landList: List[Land] = testLands(5)
      when(mockLandRepository.retrieveLands(matches(landList.head.planeId))(any())) thenReturn Future.successful(landList)
      await(service.retrieveLands(landList.head.planeId)) mustBe landList
    }
  }

  "retrieveSingleLand" must {
    "return back the campaign from the repository" in new Setup {
      when(mockLandRepository.retrieveSingleLand(matches(testLand.landId))(any())) thenReturn Future.successful(Some(testLand))
      await(service.retrieveSingleLand(testLand.landId)) mustBe Some(testLand)
    }
  }

  "createLand" must {
    "return what is returned from the repository" in new Setup {
      when(mockLandRepository.insertLand(matches(testLand))(any())) thenReturn Future.successful(testLand)
      await(service.createLand(testLand)) mustBe testLand
    }
  }

  "updateLand" must {
    "return what is returned from the repository" in new Setup {
      when(mockLandRepository.updateLand(matches(testLand))(any())) thenReturn Future.successful(Some(testLand))
      await(service.updateLand(testLand)) mustBe Some(testLand)
    }
  }

  "removeLand" must {
    "return what is returned from the repository" in new Setup {
      when(mockLandRepository.removeLand(matches(testLand.landId))(any())) thenReturn Future.successful(Some(testLand))
      await(service.removeLand(testLand.landId)) mustBe Some(testLand)
    }
  }

}
