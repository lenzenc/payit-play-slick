package services

import daos.InvoiceDAOComponent
import models.Invoice
import play.api.db.slick.Config.driver.simple._

trait InvoiceServiceComponent extends InvoiceDAOComponent {

	val invoiceService = new InvoiceServiceImpl

	trait InvoiceService {
	
		def all(implicit s: Session): List[Invoice]
    def insert(invoice: Invoice)(implicit s: Session)
    def delete(pk: Long)(implicit s: Session)
    def findByPK(pk: Long)(implicit s: Session): Invoice

	}

	class InvoiceServiceImpl extends InvoiceService {

		def all(implicit s: Session): List[Invoice] = {
			invoiceDAO.all
		}

    def insert(invoice: Invoice)(implicit s: Session) = {
      invoiceDAO.insert(invoice)
    }

    def delete(pk: Long)(implicit s: Session) = {
      invoiceDAO.delete(pk)
    }

    def findByPK(pk: Long)(implicit s: Session): Invoice = {
      invoiceDAO.findByPK(pk)
    }

	}

}