name := "payit"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.29",
  "com.typesafe.play" %% "play-slick" % "0.6.0.1"  
)     

play.Project.playScalaSettings
