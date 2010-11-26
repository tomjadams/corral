package corral.core

import Runtime._
import corral.util.Threads._
import corral.util.Logger._

final class Bootstrapper {
  lazy val socketListener = new SocketListener(Environment.port, Environment.listenSocketQueueLength)
  var running = false

  def start {
    running = true
    getRuntime.addShutdownHook(stop _)
    info("Booting system")
    socketListener.start
  }

  def stop {
    if (running) {
      info("Stopping system")
      running = false
      socketListener.stop
      shutdownPool(Environment.threadPool)
      info("Goodbye")
    }
  }
}
