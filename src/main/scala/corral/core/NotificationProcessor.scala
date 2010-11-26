package corral.core

import dispatch.json._
import corral.util._, Json._, Logger._
import java.util.UUID
import scalaz._, Scalaz._

final case class Notification(message: String) {
  override def toString = "<Notification message: " + message + ">"
}

object NotificationProcessor {
  def process(notificationPayload: String)(implicit uuid: UUID) {
    infoC("Starting to process payload")
    parseJson(notificationPayload).map(n => parseNotification(n)).|||(t => warnC("Unable to parse payload, error follows.", t))
    infoC("Completed processing payload")
  }

  def parseNotification(payload: JsValue)(implicit uuid: UUID) {
    val PayloadParser.notifications(notifications) = payload
    infoC("Payload containing " + notifications.length + " notification(s)")
    notifications.foreach(notification => {
      notification match {
        case NotificationParser(parsed) => {infoC("Parsed out notification: " + parsed)}
        case _ => () // TODO handle parse failure
      }
    })
  }
}

object PayloadParser extends Js {
  val notifications = 'notifications ? list
}

object NotificationParser extends Js with Extract[Notification] {
  val message = 'message ? str

  def unapply(js: JsValue): Option[Notification] = message.unapply(js) match {
    case Some(message) => some(Notification(message))
    case _ => none
  }
}
