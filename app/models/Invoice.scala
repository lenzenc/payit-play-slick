package models

case class Invoice(id: Option[Long], invoiceNumber: String, total: BigDecimal)