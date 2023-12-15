name := "strip-docx"

version := "1.0.0"

scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.docx4j" % "docx4j-core" % "11.4.9",
  "org.scala-lang.modules" %% "scala-xml" % "2.2.0",
  "commons-io" % "commons-io" % "2.15.1",
  // Add JAXB dependencies
  "org.glassfish.jaxb" % "jaxb-runtime" % "4.0.4",
  "jakarta.xml.bind" % "jakarta.xml.bind-api" % "4.0.1",
  "org.slf4j" % "slf4j-api" % "2.0.9", // SLF4J API
  "org.slf4j" % "slf4j-simple" % "2.0.9", // SLF4J Simple Logger for console logging

)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
)
