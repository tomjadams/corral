package corral.test

import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger
import java.net.Socket

object Collection {
  import java.util.{ArrayList, Collection}

  implicit def seqToJavaList[T](sequence: Seq[_ <: Callable[T]]): Collection[_ <: Callable[T]] = {
    val javaList = new ArrayList[Callable[T]]
    sequence.foreach(x => javaList.add(x))
    javaList
  }
}

private final class SocketClient extends Callable[Unit] {
  import corral.core.Environment
  import corral.util._, WritableSocket._

  val notification = """
    {
        "notifications": [
            {
                "message": "Hello, this is a test! Again..."
            }
        ]
    }"""

  def call = try {
    val socket = new Socket("localhost", Environment.port)
    socket.write(notification)
    socket.close
    LoadTestRunner.successes.incrementAndGet
  } catch {
    case e => {LoadTestRunner.failures.incrementAndGet; println(e)}
  }

}

object LoadTestRunner {
  import java.util.concurrent.Executors._
  import java.util.concurrent.TimeUnit._
  import Collection._
  import Timer._

  val numberOfRequests = 10000
  val numberOfThreads = 100
  lazy val executorService = newFixedThreadPool(numberOfThreads)

  val successes = new AtomicInteger(0)
  val failures = new AtomicInteger(0)

  def main(args: Array[String]) {
    startStats
    val timer = startTimer
    executorService.invokeAll[Unit](1 to numberOfRequests map(i => new SocketClient), 120L, SECONDS)
    endStats(timer.stop.ellapsedTimeSeconds)
    executorService.shutdown
    executorService.awaitTermination(10L, SECONDS)
  }

  private def startStats {
    println
    println("Executing " + numberOfRequests + " requests(s) over " + numberOfThreads + " threads")
  }

  private def endStats(duration: Double) {
    println
    println("Done in " + duration + "s; Total requests: " + numberOfRequests + "; " + (numberOfRequests.asInstanceOf[Double] / duration) + " req/s")
    println("Status " + ((successes.get.toDouble / numberOfRequests.toDouble) * 100.0) + "% (" + successes.get + " successes, " + failures.get + " failures)")
  }
}
