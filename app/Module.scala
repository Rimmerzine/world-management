import com.google.inject.AbstractModule
import controllers._
import repositories._
import services._

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[CampaignController]).to(classOf[CampaignControllerImpl]).asEagerSingleton()
    bind(classOf[CampaignService]).to(classOf[CampaignServiceImpl]).asEagerSingleton()
    bind(classOf[CampaignRepository]).to(classOf[CampaignRepositoryImpl]).asEagerSingleton()

    bind(classOf[PlaneController]).to(classOf[PlaneControllerImpl]).asEagerSingleton()
    bind(classOf[PlaneService]).to(classOf[PlaneServiceImpl]).asEagerSingleton()
    bind(classOf[PlaneRepository]).to(classOf[PlaneRepositoryImpl]).asEagerSingleton()

    bind(classOf[LandController]).to(classOf[LandControllerImpl]).asEagerSingleton()
    bind(classOf[LandService]).to(classOf[LandServiceImpl]).asEagerSingleton()
    bind(classOf[LandRepository]).to(classOf[LandRepositoryImpl]).asEagerSingleton()
  }

}
