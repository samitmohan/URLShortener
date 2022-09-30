package resources

import repository.UrlRepository
import utils._

import javax.inject.Inject

// Logic for short url and redirect

// Parameters -> UrlRepository of type repository (all traits) -> get / set
class UrlResourceHandler @Inject()(repository: UrlRepository) {
  // short url -> takes long url and may or may not return short url
  def create_short_url(resource: UrlResource): Option[UrlResource] = {
    repository.getNextId match {
      case None => None
      case Some(id) =>
        // make encoded id of long url and set urlResource (answer) and return if we set url
        val encodedId = Utils.idEncoder.encode(id)
        // answer format
        val urlResource = UrlResource(resource.originalUrl,
          Option(Config.SHORT_URL_DOMAIN + encodedId),
          Option(Utils.getCurrentUTCTimeString))
        if (repository.setURL(id, urlResource)) Option(urlResource)
        else None
    }
  }

  // lookup -> convert back from short to original reference
  def lookup(shortURL: String): Option[String] = {
    Utils.idEncoder.decode(shortURL) match {
      case Nil => None // no string
      // otherwise get url (original)
      case id :: _ => repository.getURL(id) match {
        // check if key is valid or not (ttl)
        case None => None // no og id (new)
        case Some(data) =>
          repository.incrementCounter(id)
          Option(data.originalUrl) // return original url and increment count if its not the first time
      }
    }
  }
}