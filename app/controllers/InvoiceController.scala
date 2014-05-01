package controllers

import services.InvoiceServiceComponent
import play.api.data.Form
import play.api.data.Forms._
import models.Invoice
import views._
import play.api.db.slick._
import play.api.mvc.Action

object InvoiceController extends ApplicationController with InvoiceServiceComponent {

  val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "invoiceNumber" -> nonEmptyText,
      "total" -> bigDecimal(precision=23, scale=8)
    )(Invoice.apply)(Invoice.unapply)
  )

  def index = DBAction { implicit rs =>
    Ok(html.invoices.index(invoiceService.all))
  }

  def create = Action { implicit r =>
    Ok(html.invoices.create(form))
  }

  def save = DBAction { implicit rs =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.invoices.create(formWithErrors)),
      entity => {
        invoiceService.insert(entity)
        Redirect(routes.InvoiceController.index)
      }
    )
  }

//  Coffees.findByPK(pk).list.headOption match {
//    case Some(e) => Ok(html.coffees.editForm(pk, form.fill(e), supplierSelect))
//    case None => NotFound
//  }

  def edit(pk: Long) = DBAction { implicit rs =>
    Ok(html.invoices.edit(pk, form.fill(invoiceService.findByPK(pk))))
  }

  def update(pk: Long) = TODO

  def destory(pk: Long) = DBAction { implicit rs =>
    invoiceService.delete(pk)
    Redirect(routes.InvoiceController.index)
  }

}
