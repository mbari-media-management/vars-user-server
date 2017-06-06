import java.util.concurrent.Executors
import javax.servlet.ServletContext

import org.mbari.vars.userserver.Constants
import org.mbari.vars.userserver.api.{PrefNodeV1Api, UserV1Api}
import org.mbari.vars.userserver.controllers.{PrefNodeController, UserController}
import org.mbari.vars.userserver.dao.DAOFactory
import org.scalatra.LifeCycle
import org.scalatra.swagger.ApiInfo
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext

/**
 *
 *
 * @author Brian Schlining
 * @since 2016-05-20T14:41:00
 */
class ScalatraBootstrap extends LifeCycle {

  private[this] val log = LoggerFactory.getLogger(getClass)

  val apiInfo = ApiInfo(
    """vars-user-server""",
    """VARS User names and preferences server""",
    """http://localhost:8080/api-docs""",
    """brian@mbari.org""",
    """MIT""",
    """http://opensource.org/licenses/MIT"""
  )

  override def init(context: ServletContext): Unit = {

    println("STARTING UP NOW")

    implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors()))

    val daoFactory: DAOFactory = Constants.Injector.getInstance(classOf[DAOFactory])
    val prefNodeController = new PrefNodeController(daoFactory)
    val userController = new UserController(daoFactory)

    val prefNodeApi = new PrefNodeV1Api(prefNodeController)
    val userApi = new UserV1Api()

  }

}
