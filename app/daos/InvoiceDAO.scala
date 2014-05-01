package daos

import play.api.db.slick.Config.driver.simple._
import scala.slick.lifted.Tag
import models.Invoice

trait InvoiceDAOComponent {

	val invoiceDAO = new InvoiceDAOImpl

	trait InvoiceDAO {

		def all(implicit s: Session): List[Invoice]
    def insert(invoice: Invoice)(implicit s: Session)
    def delete(pk: Long)(implicit s: Session)
    def findByPK(pk: Long)(implicit s: Session): Invoice

	}

  class Invoices(tag: Tag) extends Table[Invoice](tag, "Invoices") {
    def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
    def invoiceNumber = column[String]("invoice_number", O.NotNull)
    def total = column[BigDecimal]("total", O.NotNull)
    def * = (id, invoiceNumber, total) <> (Invoice.tupled, Invoice.unapply _)
  }

  class InvoiceDAOImpl extends InvoiceDAO {

		val invoices = TableQuery[Invoices]

		def all(implicit s: Session): List[Invoice] = {
			invoices.list
		}

    def insert(invoice: Invoice)(implicit s: Session) = {
      invoices.insert(invoice)
    }

    def delete(pk: Long)(implicit s: Session) = {
      invoices.where(_.id === pk).delete
    }

    def findByPK(pk: Long)(implicit s: Session): Invoice = {
      invoices.where(_.id === pk).firstOption.get
    }

	}

}