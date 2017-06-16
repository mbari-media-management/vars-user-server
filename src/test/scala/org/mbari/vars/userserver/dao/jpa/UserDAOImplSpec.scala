package org.mbari.vars.userserver.dao.jpa

import java.util.concurrent.TimeUnit

import org.mbari.vars.userserver.Constants
import org.mbari.vars.userserver.dao.{PrefNodeDAO, UserDAO}
import org.mbari.vars.userserver.model.User
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.concurrent.duration.{Duration => SDuration}
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * @author Brian Schlining
  * @since 2017-06-16T10:41:00
  */
class UserDAOImplSpec extends FlatSpec with Matchers with BeforeAndAfterAll {

  private[this] val daoFactory = Constants.DAOFactory
  private[this] val timeout = SDuration(2, TimeUnit.SECONDS)
  private[this] val dao = daoFactory.newUserDAO()
  private[this] val users = Seq(User("brian", "foo", "Admin", Some("MBARI"), Some("Brian"),
    Some("Schlining"), Some("brian@mbari.org"), isEncrypted = false),
    User("schlin", "bar", "Maint", isEncrypted = false),
    User("funkychicke", "baz", "ReadOnly", isEncrypted = false))

  def run[R](fn: UserDAO => R): R = Await.result(dao.runTransaction(fn), timeout)

  "UserDAOImpl" should "create" in {
    users.foreach(u => run(_.create(u)))
  }

  it should "findAll" in {
    val all = run(_.findAll())
    all.size should be (users.size)
  }

  it should "findByName" in {
    val user0 = users.head
    val opt = run(_.findByName(user0.username))
    opt should be (defined)
    val user1 = opt.get
    user1.username should be (user0.username)
    user1.password should not be (user0.password) // Should return as encrypted
    user1.role should be (user0.role)
    user1.email should be (user0.email)
    user1.affiliation should be (user0.affiliation)
    user1.lastName should be (user0.lastName)
    user1.firstName should be (user0.firstName)
    user1.isEncrypted should be (true)
  }

  it should "findByRole" in {
    val user0 = users.last
    val usersByRole = run(_.findAllByRole(user0.role))
    usersByRole.size should be (1)
    val user1 = usersByRole.head
    user1.username should be (user0.username)
    user1.password should not be (user0.password) // Should return as encrypted
    user1.role should be (user0.role)
    user1.email should be (user0.email)
    user1.affiliation should be (user0.affiliation)
    user1.lastName should be (user0.lastName)
    user1.firstName should be (user0.firstName)
    user1.isEncrypted should be (true)
  }

  it should "update" in {
    val user0 = users.head
    val opt1 = run(_.findByName(user0.username))
    opt1 should be (defined)
    val user1 = opt1.get
    user1.isEncrypted should be (true)
    val user2 = user1.copy(firstName = Some("Hakuna"), lastName = Some("Matata"))
    user2.isEncrypted should be (true)
    val opt2 = run(_.update(user2))
    opt2 should be (defined)
    opt2.get should be (user2)
    val opt3 = run(_.findByName(user2.username))
    opt3 should be (opt2)
  }


}
