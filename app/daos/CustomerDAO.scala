package daos

import models.Customer
import play.api.db.slick.Config.driver.simple._

trait CustomerDAOComponent extends DAO {

  val customerDAO = new CustomerDAOImpl

  trait CustomerDAO {

    def all: List[Customer]

  }

  class CustomerDAOImpl extends CustomerDAO {

    val customers = TableQuery[Customers]

    def all: List[Customer] = {
      db.withSession { implicit s =>
          customers.list
      }
    }

  }

  class Customers(tag: Tag) extends Table[Customer](tag, "customers") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.NotNull)
    def * = (id, name) <> (Customer.tupled, Customer.unapply _)
  }

}