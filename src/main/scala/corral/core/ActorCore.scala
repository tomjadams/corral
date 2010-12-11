package corral.core

import java.net.Socket
import corral.util._, Logger._
import java.util.UUID

object ActorCore {
  type Response = Map[Symbol, String]
}

object ReceiveActorCore {
  import ActorCore._
  import SlurpableInputStream._

  val uuidGenerator = new UuidGenerator

  def recieve(socket: Socket): (UUID, Response) = {
    implicit val uuid = uuidGenerator.generate
    try {
      infoC("Accepted connection from " + socket.getInetAddress.getHostAddress + ":" + socket.getPort)
      val payload = socket.getInputStream.slurp
      infoC("Received payload '" + payload.take(300) + "...'")
      val response = NotificationProcessor.process(payload)
      infoC("Finished processing")
      (uuid, response)
    } catch {
      case e => {
        errorC(e)
        (uuid, Map('result -> ("Error processing payload: " + e)))
      }
    }
  }
}

object SendActorCore {
  import ActorCore._
  import WritableSocket._
  import corral.util._, Id._

  def send(t: (Socket, UUID, Response)) = {
    implicit val uuid = t._2
    try {
      infoC("Writing response: '" + t._3 + "'")
      t._1.write(t._3.jsonString)
      infoC("Done with socket so closing it")
      t._1.close
    } catch {
      case e => errorC(e)
    }
  }
}
