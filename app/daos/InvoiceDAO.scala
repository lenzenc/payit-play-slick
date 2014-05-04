package daos

import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.Tag
import models.Invoice

trait InvoiceDAOComponent extends DAO {

	val invoiceDAO = new InvoiceDAOImpl

	trait InvoiceDAO {

		def all: List[Invoice]
    def insert(invoice: Invoice)
    def delete(pk: Long)
    def findByPK(pk: Long): Invoice

	}

  class Invoices(tag: Tag) extends Table[Invoice](tag, "Invoices") {
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def invoiceNumber = column[String]("invoice_number", O.NotNull)
    def total = column[BigDecimal]("total", O.NotNull)
    def * = (id, invoiceNumber, total) <> (Invoice.tupled, Invoice.unapply _)
  }

  class InvoiceDAOImpl extends InvoiceDAO {

		val invoices = TableQuery[Invoices]

		def all: List[Invoice] = {
      db.withSession { implicit s =>
        invoices.list
      }
		}

    def insert(invoice: Invoice) = {
      db.withSession { implicit s =>
          invoices.insert(invoice)
      }
    }

    def delete(pk: Long) = {
      db.withSession { implicit s =>
          invoices.where(_.id === pk).delete
      }
    }

    def findByPK(pk: Long): Invoice = {
      db.withSession { implicit s =>
          invoices.where(_.id === pk).firstOption.get
      }
    }

	}

}