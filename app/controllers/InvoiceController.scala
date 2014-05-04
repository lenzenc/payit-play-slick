package controllers

import services.InvoiceServiceComponent
import play.api.data.Form
import play.api.data.Forms._
import models.Invoice
import views._
import play.api.mvc.Action

object InvoiceController extends ApplicationController with InvoiceServiceComponent with Secured {

  val form = Form(
    mapping(
      "id" -> optional(longNumber),
      "invoiceNumber" -> nonEmptyText,
      "total" -> bigDecimal(precision=23, scale=8)
    )(Invoice.apply)(Invoice.unapply)
  )

  def index = IsAuthenticated { username => implicit r =>
    Ok(html.invoices.index(invoiceService.all))
  }

  def create = Action { implicit r =>
    Ok(html.invoices.create(form))
  }

  def save = Action { implicit r =>
    form.bindFromRequest.fold(
      formWithErrors => BadRequest(html.invoices.create(formWithErrors)),
      entity => {
        invoiceService.insert(entity)
        Redirect(routes.InvoiceController.index)
      }
    )
  }

  def edit(pk: Long) = Action { implicit r =>
    Ok(html.invoices.edit(pk, form.fill(invoiceService.findByPK(pk))))
  }

  def update(pk: Long) = TODO

  def destory(pk: Long) = Action { implicit r =>
    invoiceService.delete(pk)
    Redirect(routes.InvoiceController.index)
  }

}
