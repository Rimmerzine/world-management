import com.google.inject.AbstractModule
import controllers._
import repositories.{CampaignRepository, CampaignRepositoryImpl}
import services.{CampaignService, CampaignServiceImpl}

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[CampaignController]).to(classOf[CampaignControllerImpl]).asEagerSingleton()
    bind(classOf[CampaignService]).to(classOf[CampaignServiceImpl]).asEagerSingleton()
    bind(classOf[CampaignRepository]).to(classOf[CampaignRepositoryImpl]).asEagerSingleton()
  }

}
