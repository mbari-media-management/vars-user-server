package org.mbari.vars.userserver.api

import org.scalatra.Unauthorized
import org.scalatra.swagger.Swagger

import scala.concurrent.ExecutionContext

/**
 * @author Brian Schlining
 * @since 2017-01-19T11:07:00
 */
class AuthorizationV1Api(implicit val swagger: Swagger, val executor: ExecutionContext)
    extends ApiStack {

  override protected def applicationDescription: String = "Authorization API (v1)"

  before() {
    contentType = "application/json"
  }

  post("/") {
    authorizationService.requestAuthorization(request) match {
      case None => halt(Unauthorized())
      case Some(s) => s
    }
  }

}
