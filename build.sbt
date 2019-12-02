lazy val baseSettings: Seq[Setting[_]] = Seq(
  scalaVersion       := "2.12.10",
  scalacOptions     ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions", "-language:existentials",
    "-unchecked",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  resolvers += Resolver.sonatypeRepo("releases"),
  addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
)

lazy val algebra = project.in(file("."))
  .settings(moduleName := "algebra")
  .settings(baseSettings: _*)
  .aggregate(core, slides)
  .dependsOn(core, slides)

lazy val core = project
  .settings(moduleName := "algebra-core")
  .settings(baseSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.14.2",
      "org.typelevel" %% "cats-effect" % "2.0.0"
    ),
  )

lazy val slides = project
  .settings(moduleName := "algebra-slides")
  .settings(baseSettings: _*)
  .settings(
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "../docs",
    watchSources ++= (tutSourceDirectory.value ** "*.html").get
  ).dependsOn(core)
  .enablePlugins(TutPlugin)
