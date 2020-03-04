import com.google.inject.AbstractModule
import config.{AppConfig, AppConfigImpl}
import controllers._
import repositories._
import services._

class Module extends AbstractModule {

  override def configure(): Unit = {
    
    bind(classOf[CampaignController]).to(classOf[CampaignControllerImpl]).asEagerSingleton()
    bind(classOf[CampaignService]).to(classOf[CampaignServiceImpl]).asEagerSingleton()
    bind(classOf[CampaignRepository]).to(classOf[CampaignRepositoryImpl]).asEagerSingleton()

    bind(classOf[CreatureController]).to(classOf[CreatureControllerImpl]).asEagerSingleton()
    bind(classOf[CreatureService]).to(classOf[CreatureServiceImpl]).asEagerSingleton()
    bind(classOf[CreatureRepository]).to(classOf[CreatureRepositoryImpl]).asEagerSingleton()

    bind(classOf[AppConfig]).to(classOf[AppConfigImpl]).asEagerSingleton()
  }

}
