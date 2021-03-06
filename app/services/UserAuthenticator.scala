package services

import models.User
import daos.UserDAOModule

trait UserAuthenticatorModule {
  self: UserDAOModule =>

  val userAuthenticator: UserAuthenticator

  trait UserAuthenticator {

    def authenticate(username: String, password: String): Option[User]

  }

  class UserAuthenticatorImpl extends UserAuthenticator {

    def authenticate(username: String, password: String): Option[User] = {
      val user = userDAO.findByUsername(username)
      if (user.isDefined && user.get.password == password) user else None
    }

  }

}
