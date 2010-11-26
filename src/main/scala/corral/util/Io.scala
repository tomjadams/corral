package corral.util

import java.io.{InputStream, ByteArrayOutputStream}
import java.net.Socket

object SlurpableInputStream {
  implicit def InputStreamToSlurpableStream(in: InputStream): SlurpableInputStream = new SlurpableInputStream(in)
}

final class SlurpableInputStream(stream: InputStream) {
  // Taken from: http://blog.lostlake.org/index.php?/archives/61-A-spell-checker-in-Scala.html
  def slurp: String = {
    val bos = new ByteArrayOutputStream
    val buffer = new Array[Byte](2048)

    def read {
      stream.read(buffer) match {
        case bytesRead if bytesRead < 0 =>
        case 0 => read
        case bytesRead => bos.write(buffer, 0, bytesRead); read
      }
    }

    read
    bos.toString("UTF-8")
  }
}


object WritableSocket {
  implicit def SocketToWritableSocket(s: Socket) = new WritableSocket(s)
}

final class WritableSocket(s: Socket) {
  import java.io.PrintWriter

  lazy val out = new PrintWriter(s.getOutputStream)

  def write(m: String) {
    out.print(m)
    out.flush
  }
}
