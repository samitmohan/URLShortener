package repository

import resources.{UrlResource}

trait UrlRepository {
  // set url takes id and url
  def setURL(id: Long, data: UrlResource): Boolean
  def getURL(id: Long): Option[UrlResource] // can return url or none
  def getNextId: Option[Long] // can return none (if no id) or a long id
  def incrementCounter(id: Long): Option[Long]
}