package org.mbari.vars.userserver.api

import org.mbari.vars.userserver.controllers.UserController
import org.scalatra.{ BadRequest, NotFound }
import org.scalatra.swagger.Swagger

import scala.concurrent.ExecutionContext

class UserV1Api(controller: UserController)(implicit val swagger: Swagger, val executor: ExecutionContext)
    extends ApiStack {

  override protected def applicationDescription: String = "User V1 API"
  override protected val applicationName: Option[String] = Some.apply(getClass.getSimpleName)

  before() {
    contentType = "application/json"
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }

  get("/") {
    controller.findAll
  }

  get("/:name") {
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = "{}",
      reason = "A 'username' path parameter is required"
    )))
    controller.findByName(name).map({
      case None => halt(NotFound(body = "{}", reason = s"No user with name '$name' was found"))
      case Some(u) => u
    })
  }

  get("/role/:role") {
    val role = params.get("role").getOrElse(halt(BadRequest(
      body = "{}",
      reason = "A 'role' path parameter is required"
    )))
    controller.findAllByRole(role)
  }

  delete("/:name") {
    validateRequest()
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = "{}",
      reason = "A 'username' path parameter is required"
    )))
    controller.delete(name)
  }

  post("/") {
    validateRequest()
    val username = params.get("username").getOrElse(halt(BadRequest(
      body = "{}",
      reason = "A 'username' parameter is required"
    )))
    val password = params.get("password").getOrElse(halt(BadRequest(
      body = "{}",
      reason = "A 'password' parameter is required"
    )))
    val role = params.get("role").getOrElse(halt(BadRequest(
      body = "{}",
      reason = "A 'role' parameter is required"
    )))
    val firstName = params.get("firstName")
    val lastName = params.get("lastName")
    val affiliation = params.get("affiliation")
    val email = params.get("email")
    controller.create(username, password, role, firstName, lastName, affiliation, email)
  }

  put("/:name") {
    validateRequest()
    val username = params.get("name").getOrElse(halt(BadRequest(
      body = "{}",
      reason = "A 'username' parameter is required"
    )))
    val role = params.get("role")
    val firstName = params.get("firstName")
    val lastName = params.get("lastName")
    val affiliation = params.get("affiliation")
    val email = params.get("email")
    controller.update(username, role, firstName, lastName, affiliation, email)
      .map({
        case None => halt(NotFound(body = "{}", reason = s"Failed to update user named '$username'"))
        case Some(u) => u
      })
  }

}