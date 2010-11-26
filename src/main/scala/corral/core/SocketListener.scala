package corral.core

import corral.actors.ScalazActor._
import corral.util.Logger, Logger._
import java.net.{InetAddress, SocketException, ServerSocket}

final class SocketListener(port: Int, queueLength: Int) {
  // TODO Expose this in the configuration somewhere.
  lazy val serverSocket = new ServerSocket(port, queueLength, InetAddress.getByName(null))
  var keepGoing = true

  def start {
    info("Listening on " + serverSocket.getInetAddress + ":" + port)
    while (keepGoing && !serverSocket.isClosed) {
      handleIncomingConnections
    }
  }

  def stop {
    keepGoing = false
    if (!serverSocket.isClosed) {
      info("Stopping listening on " + serverSocket.getInetAddress + ":" + port)
      serverSocket.close
    }
  }

  private def handleIncomingConnections {
    try {
      // TODO swap out implementations here.
      scalazActor ! serverSocket.accept
    } catch {
      case se: SocketException => if (keepGoing) warn("Error accepting connection: " + se.getMessage) else Unit
      case t: Throwable => warn(t)
    }
  }
}
