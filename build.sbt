name := "world-management"
 
version := "0.1"

lazy val scalaTest: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2"

lazy val playReactiveMongo: ModuleID = "org.reactivemongo" %% "play2-reactivemongo" % "0.17.1-play27"

lazy val scoverageSettings = {
  import scoverage.ScoverageKeys
  Seq(
    ScoverageKeys.coverageExcludedPackages := "<empty>;controllers\\..*Reverse*.*;router.Routes*.*",
    ScoverageKeys.coverageMinimum := 90,
    ScoverageKeys.coverageFailOnMinimum := true,
    ScoverageKeys.coverageHighlighting := true
  )
}
      
lazy val `world-management` = (project in file("."))
  .enablePlugins(PlayScala)
  .configs(IntegrationTest)
  .settings(scoverageSettings: _*)
  .settings(
    PlayKeys.playDefaultPort := 9900,
    Defaults.itSettings,
    libraryDependencies ++= Seq(
      jdbc,
      ehcache,
      ws,
      specs2 % Test,
      guice,
      playReactiveMongo,
      scalaTest % "it,test"
    )
  )
      
scalaVersion := "2.12.2"