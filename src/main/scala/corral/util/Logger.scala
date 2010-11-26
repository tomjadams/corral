package corral.util

import UuidGenerator._
import java.util.UUID
import org.slf4j.LoggerFactory

object Logger {
  lazy val logger = LoggerFactory.getLogger("corral")

  def info(message: => String) {
    infoC(message)(fakeUuid)
  }

  def infoC(message: => String)(implicit uuid: UUID) {
    if (logger.isInfoEnabled) logger.info(uuidToString + message)
  }

  def warn(message: => String) {
    warnC(message)(fakeUuid)
  }

  def warn(message: => String, t: Throwable) {
    warnC(message, t)(fakeUuid)
  }

  def warn(t: Throwable) {
    warnC(t)(fakeUuid)
  }

  def warnC(message: => String)(implicit uuid: UUID) {
    if (logger.isWarnEnabled) logger.warn(uuidToString + message)
  }

  def warnC(message: => String, t: Throwable)(implicit uuid: UUID) {
    if (logger.isWarnEnabled) logger.warn(uuidToString + message, t)
  }

  def warnC(t: Throwable)(implicit uuid: UUID) {
    if (logger.isWarnEnabled) logger.warn(uuidToString, t)
  }

  def error(message: => String) {
    errorC(message)(fakeUuid)
  }

  def error(t: Throwable) {
    errorC(t)(fakeUuid)
  }

  def errorC(message: => String)(implicit uuid: UUID) {
    if (logger.isErrorEnabled) logger.error(uuidToString + message)
  }

  def errorC(t: Throwable)(implicit uuid: UUID) {
    if (logger.isErrorEnabled) logger.error(uuidToString + "An Error occurred, details follow.", t)
  }

  private def uuidToString(implicit uuid: UUID) = "[" + uuid.toString + "] "
}
