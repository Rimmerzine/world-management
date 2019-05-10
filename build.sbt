name := "world-management"
 
version := "0.1"

lazy val scalaTest: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2"
      
lazy val `world-management` = (project in file("."))
  .enablePlugins(PlayScala)
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    libraryDependencies ++= Seq(
      jdbc,
      ehcache,
      ws,
      specs2 % Test,
      guice,
      scalaTest % "it,test",
    )
  )
      
scalaVersion := "2.12.2"