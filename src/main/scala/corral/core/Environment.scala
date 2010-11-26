package corral.core

import java.util.concurrent.Executors

object Environment {
  val port = 3838

  val listenSocketQueueLength = 1000

  lazy val threadPool = Executors.newFixedThreadPool(50)
}
