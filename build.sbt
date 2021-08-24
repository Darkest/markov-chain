name := "markov-chain"

version := "0.1"

scalaVersion := "2.13.5"


val CatsVersion = "2.6.0"
val CatsEffectsVersion = "3.1.0"
val CirceVersion = "0.13.0"
val CirceGenericExVersion = "0.13.0"
val CirceConfigVersion = "0.8.0"
val KindProjectorVersion = "0.11.0"
val LogbackVersion = "1.2.3"
val Slf4jVersion = "1.7.30"
val ScalaCheckVersion = "1.15.1"
val ScalaTestVersion = "3.2.3"
val ScalaTestPlusVersion = "3.2.2.0"
val HoconVersion = "1.2.0"
val ScoptVersion = "4.0.1"
val TofuVersion = "0.10.3"


libraryDependencies ++= Seq(
  "com.typesafe" % "config" % HoconVersion,
  "org.typelevel" %% "cats-core" % CatsVersion,
  "org.typelevel" %% "cats-effect" % CatsEffectsVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "io.circe" %% "circe-literal" % CirceVersion,
  "io.circe" %% "circe-generic-extras" % CirceGenericExVersion,
  "io.circe" %% "circe-parser" % CirceVersion,
  "io.circe" %% "circe-config" % CirceConfigVersion,
  "ch.qos.logback" % "logback-classic" % LogbackVersion,
  "tf.tofu" %% "tofu-core" % TofuVersion,
  "org.scalacheck" %% "scalacheck" % ScalaCheckVersion % Test,
  "org.scalatest" %% "scalatest" % ScalaTestVersion % Test,
  "org.scalatestplus" %% "scalacheck-1-14" % ScalaTestPlusVersion % Test,
  "org.typelevel" %% "cats-testkit-scalatest" % "2.1.5" % Test,
  "com.github.scopt" %% "scopt" % ScoptVersion
)
