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

package org.mbari.vars.userserver.model

import java.util.prefs.{ AbstractPreferences, Preferences }

import org.mbari.vars.userserver.dao.PrefNodeDAO

/**
 * @author Brian Schlining
 * @since 2017-06-05T11:22:00
 */
class WebPrefs(val dao: PrefNodeDAO, parentPrefs: AbstractPreferences = null, name: String = "")
    extends AbstractPreferences(parentPrefs, name) {

  override def flushSpi(): Unit = { /* do nothing */ }

  override def childSpi(name: String): AbstractPreferences = new WebPrefs(dao, this, name)

  override def syncSpi(): Unit = { /* do nothing */ }

  override def putSpi(key: String, value: String): Unit = {
    dao.findByNodeNameAndKey(absolutePath(), key) match {
      case None =>
        dao.create(PrefNode(absolutePath(), key, value))
      case Some(node) =>
        if (node.value != value) dao.update(node.copy(value = value))
    }
  }

  override def childrenNamesSpi(): Array[String] = {
    val parentNodeName = absolutePath()
    val nodes = dao.findByNodeNameLike(parentNodeName)
    val childNames = for (node <- nodes) yield {
      val nodeName = node.name
      // Strip off base path
      var childPath = nodeName.substring(parentNodeName.length, nodeName.length)
      // If path starts with '/' remove it
      if (childPath.startsWith("/")) childPath = childPath.substring(1, childPath.length)
      // Take up to the first '/' (or all if no slash is present)
      if (childPath.indexOf("/") >= 0) childPath.substring(0, childPath.indexOf("/"))
      else childPath
    }
    childNames.toArray.distinct
  }

  override def removeSpi(key: String): Unit = dao.findByNodeNameAndKey(absolutePath(), key) match {
    case None => // do nothing
    case Some(node) => dao.delete(node)
  }

  override def keysSpi(): Array[String] = dao.findByNodeName(absolutePath())
    .map(_.key)
    .toArray

  override def removeNodeSpi(): Unit = dao.findByNodeNameLike(absolutePath())
    .foreach(dao.delete)

  override def getSpi(key: String): String = dao.findByNodeNameAndKey(absolutePath(), key) match {
    case None => null
    case Some(node) => node.value
  }
}
