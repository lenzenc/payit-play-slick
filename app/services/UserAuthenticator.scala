package services

import models.User
import daos.UserDAOComponent

trait UserAuthenticatorComponent extends ServiceComponent with UserDAOComponent {

  val userAuthenticator = new UserAuthenticatorImpl

  trait UserAuthenticator {

    def authenticate(username: String, password: String): Option[User]

  }

  class UserAuthenticatorImpl extends UserAuthenticator {

    def authenticate(username: String, password: String): Option[User] = {
      val user = userDAO.findByUsername(username)
      if (user.isDefined) {
        if (user.get.password == password) {
          user
        } else {
          None
        }
      } else {
        None
      }

    }

  }

}
