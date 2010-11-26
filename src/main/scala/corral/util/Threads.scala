package corral.util

import corral.util.Logger._
import java.util.concurrent.{ExecutorService, TimeUnit}, TimeUnit._

object Threads {
  implicit def function0OfUnitToRunnable(f: Function0[Unit]): Runnable = new Runnable {def run {f.apply}}

  implicit def function0OfUnitToThread(f: Function0[Unit]): Thread = new Thread(new Runnable {def run {f.apply}})

  def shutdownPool(pool: ExecutorService) {
    info("Shutting down thread pool")
    pool.shutdown
    try {
      if (!pool.awaitTermination(2, MINUTES)) {
        warn("Thread pool did not terminate, forcing shutdown")
        pool.shutdownNow
        if (!pool.awaitTermination(60, SECONDS)) {
          warn("Thread pool did not terminate after waiting, you should kill this process manually")
        }
      }
    } catch {
      case ie: InterruptedException => {
        warn(ie)
        pool.shutdownNow
        Thread.currentThread.interrupt
      }
    }
  }
}
