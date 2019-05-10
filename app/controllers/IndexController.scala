package controllers

import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

class OkControllerImpl @Inject()(val controllerComponents: ControllerComponents) extends OkController

trait OkController extends BaseController {

  def ok: Action[AnyContent] = Action { implicit request =>
    Ok("Oks")
  }

}