/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: Krautmail Server
 * Date: 08.10.2013
 * Time: 21:21
 */

import sbt._

object KrautMail extends Build {
  lazy val root = Project(id = "Root", base = file("."))
    .aggregate(baseComponents, collectors, runners)

  lazy val baseComponents = Project(id = "Base", base = file("base"))

  lazy val collectors = Project(id = "Collectors", base = file("collectors"))

  lazy val runners = Project(id = "Runners", base = file("runners"))
    .aggregate(devRunner)

  lazy val devRunner = Project(id = "DevRunner", base = file("runners/devrunner"))
}
