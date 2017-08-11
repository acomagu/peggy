lazy val root = (project in file("."))
  .settings(
    name := "slock"
  )
  .aggregate(cleanArchitectureSupports, domains, commons, usecases, flywaysql, webapp)

lazy val akkaVersion = "10.0.9"
lazy val scalikeVersion = "2.5.2"

def commonSettings = Seq(
  organization := "me.acomagu",
  version := "1.0.0",
  scalaVersion := "2.12.3",
  javacOptions ++= Seq("-encoding", "UTF-8"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Ywarn-unused",
    "-Ywarn-unused-import"
  )
)

/**
  * 各種モジュールで利用する共通のクラス群を配置
  */
lazy val commons = (project in file("modules/commons"))
  .settings(commonSettings: _*)
  .settings(
    name := "commons",
    resolvers ++= Seq(
      Resolver.url("github repo for hamsters", url("http://scala-hamsters.github.io/hamsters/releases/"))(Resolver.ivyStylePatterns)
    ),
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.1",
      "com.typesafe.akka" %% "akka-http" % akkaVersion,
      "io.github.scala-hamsters" %% "hamsters" % "1.4.0",
      "org.slf4j" % "slf4j-api" % "1.7.25", // facadeにslf4jを
      "ch.qos.logback" % "logback-classic" % "1.2.3",  // アダプタおよびロギングライブラリにlogbackを
      "org.scalatest" %% "scalatest" % "3.0.3"
    )
  )

/**
  * 契約
  * クリーンアーキテクチャを支える定義クラス群を配置
  */
lazy val cleanArchitectureSupports = (project in file("modules/cleanArchitectureSupports"))
  .settings(commonSettings: _*)
  .settings(
    name := "cleanArchitecutureSupports"
  )

/**
  * ドメイン層
  */
lazy val domains = (project in file("modules/domains"))
  .settings(commonSettings: _*)
  .settings(
    name := "domains",
    libraryDependencies ++= Seq(
    )
  )
  .dependsOn(commons)

/**
  * ユースケース層
  */
lazy val usecases = (project in file("modules/usecases"))
  .settings(commonSettings: _*)
  .settings(
    name := "usecases"
  )
  .dependsOn(domains, cleanArchitectureSupports, commons)

/**
  * アダプタ層
  */
lazy val adapters = (project in file("modules/adapters"))
  .settings(commonSettings: _*)
  .settings(
    name := "adapters",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
      "org.scalikejdbc" %% "scalikejdbc"       % scalikeVersion,
      "org.scalikejdbc" %% "scalikejdbc-config" % scalikeVersion,
      "org.scalikejdbc" %% "scalikejdbc-jsr310" % scalikeVersion,
      "org.skinny-framework" %% "skinny-orm"   % "2.3.8",
      "org.postgresql" % "postgresql" % "9.4.1209"
    )
  )
  .dependsOn(usecases, commons)

/**
  * WebApplication
  */
lazy val webapp = (project in file("modules/webapp"))
  .settings(commonSettings: _*)
  .settings(
    name := "webapp",
    libraryDependencies ++= Seq(
      "commons-daemon" % "commons-daemon" % "1.0.15"
    )
  )
  .dependsOn(adapters)

/**
  * DBマイグレーション
  */
lazy val flywaysql = (project in file("modules/flyway-sql"))
  .settings(
    name := "flywaysql",
    libraryDependencies ++= Seq(
      "org.postgresql" % "postgresql" % "9.4.1209"
    )
  )

