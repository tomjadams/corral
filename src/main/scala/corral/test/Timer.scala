package corral.test

sealed trait Timer {
  def start: Timer

  def stop: Timer

  def ellapsedTimeMillis: Long

  def ellapsedTimeSeconds: Double = ellapsedTimeMillis match {
    case 0L => 0D
    case x => x.asInstanceOf[Double] / 1000D
  }
}

object Timer {
  def startTimer: Timer = timer.start

  private def timer: Timer = StoppedTimer(0L)
}

private final case class StoppedTimer(ellapsedTime: Long) extends Timer {
  override def start = StartedTimer(System.currentTimeMillis)

  override def stop = this

  override def ellapsedTimeMillis = ellapsedTime
}

private final case class StartedTimer(startTime: Long) extends Timer {
  override def start = this

  override def stop = StoppedTimer(System.currentTimeMillis - startTime)

  override def ellapsedTimeMillis = error("Not stopped")
}
