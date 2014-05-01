package daos

import models.User
import play.api.db.slick.Config.driver.simple._

trait UserDAOComponent {

  val userDAO = new UserDAOImpl

  trait UserDAO {

    def all(implicit s: Session): List[User]

  }

  class UserDAOImpl extends UserDAO {

    val users = TableQuery[Users]

    def all(implicit s: Session): List[User] = {
      users.list
    }

  }

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def firstName = column[String]("first_name", O.NotNull)
    def lastName = column[String]("last_name", O.NotNull)
    def username = column[String]("username", O.NotNull)
    def password = column[String]("password", O.NotNull)
    def * = (id, firstName, lastName, username, password) <> (User.tupled, User.unapply _)
  }

}
