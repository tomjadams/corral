package corral.util

import dispatch.json._
import Json._

trait Id[T] {
  val value: T

  def json(implicit j: Json[T]): JsValue = j.json(value)

  def jsonString(implicit j: Json[T]): String = JsValue.toJson(this.json)
}

object Id {
  implicit def AnyTo[T](t: T) = new Id[T] {val value = t}
}
