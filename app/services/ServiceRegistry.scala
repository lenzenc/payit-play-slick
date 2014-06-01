package services

import daos.DAORegistry

trait ServiceRegistry extends UserAuthenticatorModule with InvoiceServiceModule with DAORegistry {

  val userAuthenticator = new UserAuthenticatorImpl
  val invoiceService = new InvoiceServiceImpl

}
