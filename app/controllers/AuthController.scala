package controllers

import play.api.data.Form
import play.api.data.Forms._
import views._
import play.api.mvc._

trait Secured {

  def username(request: RequestHeader) = request.session.get("username")

  def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.AuthController.login)

  def IsAuthenticated(f: => String => Request[AnyContent] => Result) =
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
  }

}

object AuthController extends ApplicationController {

  val form = Form(
    tuple(
      "username" -> text,
      "password" -> text
    ) verifying ("Invalid username or password", result => result match {
      case (username, password) => check(username, password)
    })
  )

  def login = Action { implicit r =>
    Ok(html.login(form))
  }

  def logout = Action {
    Redirect(routes.AuthController.login).withNewSession.flashing(
      "success" -> "You are now logged out."
    )
  }

  def authenticate = Action { implicit r =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.InvoiceController.index).withSession("username" -> user._1)
    )
  }

  def check(username: String, password: String) = {
    (username == "admin" && password == "1234")
  }

}
