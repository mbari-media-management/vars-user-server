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

package org.mbari.vars.userserver

import com.google.inject.{ Binder, Module }
import org.mbari.vars.userserver.dao.DAOFactory
import org.mbari.vars.userserver.dao.jpa.JPADAOFactoryImpl

/**
 * @author Brian Schlining
 * @since 2017-06-06T15:48:00
 */
class InjectorModule extends Module {
  override def configure(binder: Binder): Unit = {
    binder.install(new vars.jpa.InjectorModule)
    binder.bind(classOf[DAOFactory]).to(classOf[JPADAOFactoryImpl])
  }
}