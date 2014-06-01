import play.api.GlobalSettings
import controllers.Controllers

object Global extends GlobalSettings {

  override def getControllerInstance[A](controllerClass: Class[A]): A = Controllers(controllerClass)

}
