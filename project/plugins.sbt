// https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.2")

// https://github.com/xerial/sbt-pack
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.11")

// https://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.4.2")

// https://olafurpg.github.io/scalafmt
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")

// https://github.com/sbt/sbt-assembly
// addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.8")

// https://github.com/earldouglas/xsbt-web-plugin
addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "4.0.2")

// https://github.com/sbt/sbt-native-packager
// addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.6")

// https://github.com/sbt/sbt-header
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "5.2.0")

resolvers += Resolver.sonatypeRepo("releases")
