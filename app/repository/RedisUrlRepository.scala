package repository

import com.redis._
import com.redis.serialization.Parse.Implicits.parseInt
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import play.api.Logger
import resources.{UrlResource}
import utils._

import javax.inject.Singleton

// Clients -> database of redis
@Singleton
// implementation of url-repository
class RedisUrlRepository extends UrlRepository {
  // log (getClass -> returns class (2.getClass) -> int)
  private val logger = Logger(getClass)
  val url_key_prefix = "url:" // prefix
  val url_counter_key_prefix = "url-counter:"
  val unique_id_key = "url-id:id"
  val clients = new RedisClientPool(Config.REDIS_HOST, Config.REDIS_PORT) // new data

  // set-url
  def setURL(id: Long, data: UrlResource): Boolean = {
    val key = url_key_prefix + id
    val value = data.asJson.noSpaces
    logger.debug(s"Key =  $key value = $value")
    clients.withClient(_.setnx(key, value)) // redis set if not exists (no duplicates)
    // setting ttl of 100 my default
    clients.withClient(_.expire(key, 1000))
  }



  def getURL(id: Long): Option[UrlResource] = {
    val key = url_key_prefix + id
    logger.debug(s"Redis getkey = $key")
    clients.withClient {
      client: RedisClient =>
        client.get[String](key) match {
          // add TTL
          case None => None // no key in the first place
          case Some(value) => decode[UrlResource](value) match {
            // return decoded string -> if no val -> left, otherwise return resource
            case Left(error) => None
            case Right(resource) => Option(resource)
          }
        }
    }
  }

  def getNextId: Option[Long] = {
    val key = unique_id_key // don't need current id for this
    logger.debug(s"Redis increment key = $key")
    clients.withClient(_.incr(key)) // increments
  }

  def incrementCounter(id: Long): Option[Long] = {
    val key = url_counter_key_prefix + id // previous key + id
    logger.debug(s"Redis increment count = $key")
    clients.withClient(_.incr(key))
  }
}