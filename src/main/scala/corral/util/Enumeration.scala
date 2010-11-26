package corral.util

object Enumeration {
  implicit def enumToList[A](enum: java.util.Enumeration[A]): List[A] = if (enum.hasMoreElements) enum.nextElement :: enumToList(enum) else Nil
}
