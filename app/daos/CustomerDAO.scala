package daos

import models.Customer
import scala.slick.lifted.Tag
import play.api.db.slick.Config.driver.simple._

trait CustomerDAOComponent {

  val customerDAO = new CustomerDAOImpl

  trait CustomerDAO {

    def all(implicit s: Session): List[Customer]

  }

  class CustomerDAOImpl extends CustomerDAO {

    val customers = TableQuery[Customers]

    def all(implicit s: Session): List[Customer] = {
      customers.list
    }

  }

  class Customers(tag: Tag) extends Table[Customer](tag, "customers") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.NotNull)
    def * = (id, name) <> (Customer.tupled, Customer.unapply _)
  }

}