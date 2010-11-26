package corral.util

import java.util.UUID

object UuidGenerator {
  lazy val fakeUuid = UUID.fromString("00000000-0000-0000-0000-000000000000")
}

final class UuidGenerator {
 def generate: UUID = UUID.randomUUID
}
