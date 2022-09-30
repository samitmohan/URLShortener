package utils

import org.pico.hashids.Hashids
import java.time.format.DateTimeFormatter
import java.time.{OffsetDateTime, ZoneOffset}

object Utils {
  def getCurrentUTCTimeString: String = {
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val utc = OffsetDateTime.now(ZoneOffset.UTC)
    utc.format(format) // val res0: String = 2022-09-12T05:00:40.821+0000 (currentTime)
  }

  val idEncoder: Hashids = Hashids.reference(Config.ENCODER_SALT)
}