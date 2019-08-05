package config

import javax.inject.Inject
import play.api.Configuration

class AppConfigImpl @Inject()(configuration: Configuration) extends AppConfig {

  val databaseName: String = configuration.get[String]("mongodb.name")

}

trait AppConfig {

  def databaseName: String

}