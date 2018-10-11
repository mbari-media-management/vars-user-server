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

package org.mbari.vars.userserver.dao

import javax.inject.Inject

import org.mbari.vars.userserver.model.User
import vars.knowledgebase.KnowledgebaseDAOFactory

/**
 * @author Brian Schlining
 * @since 2017-06-05T10:37:00
 */
trait UserDAO extends DAO {

  def create(user: User): Unit

  def update(user: User): Option[User]

  def delete(user: User): Unit

  def findByName(name: String): Option[User]

  def findAllByRole(name: String): Iterable[User]

  def findAll(): Iterable[User]
}
