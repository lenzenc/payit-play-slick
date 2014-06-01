package daos

trait DAORegistry extends
  CustomerDAOModule with
  UserDAOModule with
  InvoiceDAOModule
{

  val customerDAO = new CustomerDAOImpl
  val userDAO = new UserDAOImpl
  val invoiceDAO = new InvoiceDAOImpl

}
