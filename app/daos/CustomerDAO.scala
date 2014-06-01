package daos

import models.Customer

trait CustomerDAOModule {

  import play.api.db.slick.Config.driver.simple._

  val customerDAO: CustomerDAO

  trait CustomerDAO {

    def all: List[Customer]

  }

  class CustomerDAOImpl extends CustomerDAO with DAO {

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