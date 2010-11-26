package corral.actors

import scalaz._, scalaz.concurrent._, Scalaz._
import corral.core.{Environment, ActorCore}

object ScalazActor {
  implicit val pool = Environment.threadPool
  implicit val s = Strategy.Executor

  val scalazActor = actor(ActorCore.process _)
}
