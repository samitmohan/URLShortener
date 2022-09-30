  // def getTTL(id : Long): Option[Long]
  // def setTTL(id: Long, ttl_val: Int, data: TTLResource): Boolean

  // TTL by user.
//  def setTTL(id: Long, ttl_val: Int, data: TTLResource): Boolean = {
//    val key = url_key_prefix + id
//    val value = ttl_val
//    clients.withClient(_.set(key, value))
//  }
//
//  def getTTL(id: Long) = {
//    val key = url_key_prefix + id
//    clients.withClient {
//      client: RedisClient =>
//        client.get[Int](key) match {
//          case None => None // no ttl set.
//          case Some(ttl_value) => Option(ttl_value)
//        }
//    }
//  }
//
// TTLResource.scala -> 

// package resources

// case class TTLResource(ttl_val : Int)
// TTL Resource Handler ->

// package resources

// import play.api.mvc.Results.{BadRequest, Conflict}
// import repository.UrlRepository

// import javax.inject.Inject

// class TTLResourceHandler @Inject()(repository: UrlRepository) {
//   def isValidTTL(resource: TTLResource) : Option[TTLResource] = {
//     // if ttl expired -> throw error

//     repository.getTTL match {
//       case Some(error) => BadRequest("URL Expired : " + error.toString)
//     }
//   }
// }
