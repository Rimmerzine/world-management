import com.google.inject.AbstractModule
import controllers._

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[OkController]).to(classOf[OkControllerImpl]).asEagerSingleton()
  }

}
