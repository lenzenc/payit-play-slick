package daos

import models.User

trait UserDAOModule {

  import play.api.db.slick.Config.driver.simple._

  val userDAO: UserDAO

  trait UserDAO {

    def all: List[User]
    def findByUsername(username: String): Option[User]

  }

  class UserDAOImpl extends UserDAO with DAO {

    val users = TableQuery[Users]

    def all: List[User] = {
      db.withSession { implicit s =>
          users.list
      }
    }

    def findByUsername(username: String): Option[User] = {
      db.withSession { implicit s =>
        users.where(_.username === username).firstOption
      }
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
