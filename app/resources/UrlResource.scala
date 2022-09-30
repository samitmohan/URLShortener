package resources

// convert this into json -> jackson.

case class UrlResource(originalUrl: String, shortUrl: Option[String], creationDate: Option[String])