package corral.core

import java.util.concurrent.Executors

object Environment {
  val port = 3838

  val listenSocketQueueLength = 10000

  lazy val threadPool = Executors.newFixedThreadPool(5)
}
