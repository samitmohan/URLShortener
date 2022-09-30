package utils

import com.typesafe.config.ConfigFactory

// https://stackoverflow.com/questions/21607745/specific-config-by-environment-in-scala
// Having a clear separation between configurations and code allow us to customise its execution to the environment where it runs in.
// the script loads configurations first from environment variables

object Config {
  private val conf: com.typesafe.config.Config = ConfigFactory.load
  val ENCODER_SALT: String = conf.getString("shortener.encoder-salt")
  val SHORT_URL_DOMAIN = conf.getString("shortener.short-url-domain")
  val REDIS_HOST = conf.getString("shortener.redis.host")
  val REDIS_PORT = conf.getInt("shortener.redis.port")
}