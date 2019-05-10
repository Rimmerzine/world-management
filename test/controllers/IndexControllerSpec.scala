package controllers

import helpers.UnitSpec
import play.api.mvc.{ControllerComponents, Result}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.Future

class IndexControllerSpec extends UnitSpec {

  trait Setup {
    val controller: OkController = new OkController() {
      def controllerComponents: ControllerComponents = Helpers.stubControllerComponents()
    }
  }

  "index" must {
    "redirect the user to the home page" in new Setup {
      val result: Future[Result] = controller.ok()(FakeRequest())
      status(result) mustBe OK
    }
  }
}
