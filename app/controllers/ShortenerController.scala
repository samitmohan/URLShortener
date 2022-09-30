package controllers

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import play.api.Logger
import play.api.mvc._
import resources._

import javax.inject.Inject

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

class ShortenerController @Inject()(cc: ControllerComponents, resourceHandler: UrlResourceHandler)
  extends AbstractController(cc) {

  //  def index() = Action { implicit request: Request[AnyContent] =>
  //    Ok(views.html.index())
  //  }

  private val logger = Logger(getClass)

  def create_short_url: Action[AnyContent] = Action { implicit request =>
    logger.debug(s"$request ${request.body}")

    request.body.asJson match {
      case None => BadRequest("Expected JSON Body")
      case Some(jsonBody) => decode[UrlResource](jsonBody.toString()) match {
        case Left(error) => BadRequest("Invalid JSON : " + error.toString)
        case Right(resource) => resourceHandler.create_short_url(resource) match {
          // create short url
          // cases -> cant encode, otherwise encode
          case None => Conflict("Cannot encode URL")
          case Some(shortUrl) => Created(shortUrl.asJson.noSpaces).as("application/json")
        }
      }
    }
  }

  // redirect to original url
  def redirect(short_url: String) = Action { implicit request =>
    logger.debug(s"$request")

    resourceHandler.lookup(short_url) match {
      case None => NotFound
      case Some(originalUrl) => MovedPermanently(originalUrl)
//        .withHeaders(CACHE_CONTROL -> "no-cache")
    }
  }
}