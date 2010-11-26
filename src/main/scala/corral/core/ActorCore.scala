package corral.core

import java.net.Socket
import corral.util._, Logger._, SlurpableInputStream._, WritableSocket._

object ActorCore {
  val uuidGenerator = new UuidGenerator

  def process(socket: Socket) = {
    implicit val udid = uuidGenerator.generate
    try {
      infoC("Accepted connection from " + socket.getInetAddress.getHostAddress + ":" + socket.getPort)
      val payload = socket.getInputStream.slurp
      infoC("Received payload '" + payload.take(300) + "...'")
      NotificationProcessor.process(payload)
      socket.write("{}")
      socket.close
      infoC("Done with socket so closing it")
    } catch {
      case e => errorC(e)
    }
  }
}
