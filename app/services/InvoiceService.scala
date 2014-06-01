package services

import models.Invoice
import daos.InvoiceDAOModule

trait InvoiceServiceModule {
  self: InvoiceDAOModule =>

	val invoiceService: InvoiceService

	trait InvoiceService {
	
		def all: List[Invoice]
    def insert(invoice: Invoice)
    def delete(pk: Long)
    def findByPK(pk: Long): Invoice

	}

	class InvoiceServiceImpl extends InvoiceService {

		def all: List[Invoice] = invoiceDAO.all

    def insert(invoice: Invoice) = invoiceDAO.insert(invoice)

    def delete(pk: Long) = invoiceDAO.delete(pk)

    def findByPK(pk: Long): Invoice = invoiceDAO.findByPK(pk)

	}

}