/*
 * Copyright 2017 Monterey Bay Aquarium Research Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mbari.vars.userserver.api

import org.mbari.vars.userserver.controllers.PrefNodeController
import org.scalatra.{ BadRequest, NotFound }

import scala.concurrent.ExecutionContext

/**
 * @author Brian Schlining
 * @since 2017-06-05T14:22:00
 */
class PrefNodeV1Api(controller: PrefNodeController)(implicit val executor: ExecutionContext)
    extends ApiStack {

  before() {
    contentType = "application/json"
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }

  get("/:name") {
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = """{"error": "A 'name' parameter is required"}""",
    )))
    controller.findByNodeName(name)
  }

  get("/:name/:key") {
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = """{"error": "A 'name' parameter is required"}""",
    )))
    val key = params.get("key").getOrElse(halt(BadRequest(
      body = """{"error": "A 'key' parameter is required"}""",
    )))
    controller.findByNodeNameAndKey(name, key)
      .map({
        case None => halt(NotFound(body = s"""{"error": "Did nor find pref with name: '$name and key '$key'"}"""))
        case Some(p) => p
      })
  }

  get("/startswith/:name") {
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = """{"error": "A 'name' parameter is required"}""",
    )))
    controller.findByNodeNameLike(name)
  }

  post("/") {
    validateRequest()
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = """{"error": "A 'name' parameter is required"}""",
    )))
    val key = params.get("key").getOrElse(halt(BadRequest(
      body = """{"error": "A 'key' parameter is required"}""",
    )))
    val value = params.get("value").getOrElse(halt(BadRequest(
      body = """{"error": "A 'value' parameter is required"}""",
    )))
    controller.create(name, key, value)
  }

  put("/:name/:key") {
    validateRequest()
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = """{"error": "A 'name' parameter is required"}""",
    )))
    val key = params.get("key").getOrElse(halt(BadRequest(
      body = """{"error": "A 'key' parameter is required"}""",
    )))
    val value = params.get("value").getOrElse(halt(BadRequest(
      body = """{"error": "A 'value' parameter is required"}""",
    )))
    controller.update(name, key, value)
  }

  delete("/:name/:key") {
    validateRequest()
    val name = params.get("name").getOrElse(halt(BadRequest(
      body = """{"error": "A 'name' parameter is required"}""",
    )))
    val key = params.get("key").getOrElse(halt(BadRequest(
      body = """{"error": "A 'key' parameter is required"}""",
    )))
    controller.delete(name, key)
  }

}