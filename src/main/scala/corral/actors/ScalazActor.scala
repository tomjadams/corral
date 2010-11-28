package corral.actors

import corral.core.{Environment, ReceiveActorCore, SendActorCore}
import java.net.Socket
import java.util.UUID
import scalaz._, scalaz.concurrent._, Scalaz._

object ScalazActors {
  import corral.core.ActorCore._

  implicit val pool = Environment.threadPool
  implicit val s = Strategy.Executor

  val scalazReceiveActor = actor((s: Socket) => {
    val response = ReceiveActorCore.recieve(s)
    val x = (s, response._1, response._2)
    scalazSendActor ! x
  })

  val scalazSendActor = actor((t: (Socket, UUID, Response)) => {
    SendActorCore.send(t)
  })
}
