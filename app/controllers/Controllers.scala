package controllers

import services.ServiceRegistry
import scala.reflect.{classTag, ClassTag}

object Controllers
{

  def apply[A](clazz: Class[A]) = instances(clazz).asInstanceOf[A]

  private val instances: Map[Class[_ <: Any], Any] = {
    def controller[A: ClassTag](instance: A) = classTag[A].runtimeClass -> instance

    Map(
      controller[AuthController](new AuthController with ServiceRegistry),
      controller[InvoiceController](new InvoiceController with ServiceRegistry)
    )
  }

}
