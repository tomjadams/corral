import sbt._

class CorralProject(info: ProjectInfo) extends DefaultProject(info) {
  // Repos
  val snapshots = "nexus" at "http://www.scala-tools.org/repo-snapshots/"
  val notnoop = "notnoop" at "http://notnoop.github.com/m2-repo"

  // Runtime dependencies
  val jacksonMapperAsl = "org.codehaus.jackson" % "jackson-mapper-asl" % "1.4.0" withSources()
  val logbackCore = "ch.qos.logback" % "logback-core" % "0.9.24" withSources()
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "0.9.24+" withSources()
  val scalazCore = "com.googlecode.scalaz" %% "scalaz-core" % "5.0" withSources()
  val dispatch = "net.databinder" %% "dispatch-json" % "0.7.5"

  // Test dependencies
  val specs = "org.scala-tools.testing" %% "specs" % "1.6.6-SNAPSHOT" % "test->default" withSources ()

  // TODO Add some new endpoints for other actor impls.
//  override def mainClass = Some("corral.core.Main")
  // TODO Add in load tests here.
}
