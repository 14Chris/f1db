plugins {
    distribution
    id("com.f1db")
    id("org.jreleaser") version "1.17.0"
}

group = "com.f1db"

val currentSeasonProperty = project.property("currentSeason") as String
val currentSeasonFinishedProperty = project.property("currentSeasonFinished") as String

f1db {
    sourceDir.set(layout.projectDirectory.dir("src/data"))
    outputDir.set(layout.buildDirectory.dir("data"))
    currentSeason.set(currentSeasonProperty.toInt())
    currentSeasonFinished.set(currentSeasonFinishedProperty.toBoolean())
}

repositories {
    mavenCentral()
}

distributions {
    create("csv") {
        distributionBaseName.set("f1db-csv")
        contents {
            from(layout.buildDirectory.dir("data/csv"))
            into("/")
            include("*.csv")
        }
    }
    create("json-single") {
        distributionBaseName.set("f1db-json-single")
        contents {
            from(layout.buildDirectory.file("data/json/f1db.json"))
            from(layout.projectDirectory.file("src/schema/current/single/f1db.schema.json"))
            into("/")
        }
    }
    create("json-splitted") {
        distributionBaseName.set("f1db-json-splitted")
        contents {
            from(layout.buildDirectory.dir("data/json")) {
                include("*.json")
                exclude("f1db.json")
            }
            from(layout.projectDirectory.dir("src/schema/current/splitted")) {
                include("*.schema.json")
            }
            into("/")
        }
    }
    create("smile-single") {
        distributionBaseName.set("f1db-smile-single")
        contents {
            from(layout.buildDirectory.file("data/smile/f1db.sml"))
            from(layout.projectDirectory.file("src/schema/current/single/f1db.schema.json"))
            into("/")
        }
    }
    create("smile-splitted") {
        distributionBaseName.set("f1db-smile-splitted")
        contents {
            from(layout.buildDirectory.dir("data/smile")) {
                include("*.sml")
                exclude("f1db.sml")
            }
            from(layout.projectDirectory.dir("src/schema/current/splitted")) {
                include("*.schema.json")
            }
            into("/")
        }
    }
    create("sql-mysql") {
        distributionBaseName.set("f1db-sql-mysql")
        contents {
            from(layout.buildDirectory.file("data/sql/f1db-sql-mysql.sql"))
            into("/")
        }
    }
    create("sql-postgresql") {
        distributionBaseName.set("f1db-sql-postgresql")
        contents {
            from(layout.buildDirectory.file("data/sql/f1db-sql-postgresql.sql"))
            into("/")
        }
    }
    create("sql-sqlite") {
        distributionBaseName.set("f1db-sql-sqlite")
        contents {
            from(layout.buildDirectory.file("data/sql/f1db-sql-sqlite.sql"))
            into("/")
        }
    }
    create("sqlite") {
        distributionBaseName.set("f1db-sqlite")
        contents {
            from(layout.buildDirectory.file("data/sqlite/f1db.db"))
            into("/")
        }
    }
}

jreleaser {
    project {
        name.set("f1db")
        versionPattern.set("CALVER:YYYY.MINOR.MICRO[.MODIFIER]")
        description.set("F1DB Open Source Formula 1 Database")
        license.set("CC-BY-4.0")
        copyright.set("F1DB")
    }
    files {
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-csv-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-csv.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-json-single-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-json-single.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-json-splitted-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-json-splitted.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-smile-single-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-smile-single.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-smile-splitted-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-smile-splitted.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-sql-mysql-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-sql-mysql.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-sql-postgresql-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-sql-postgresql.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-sql-sqlite-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-sql-sqlite.zip")
        }
        artifact {
            setPath(layout.buildDirectory.file("distributions/f1db-sqlite-{{projectVersion}}.zip").get().asFile.path)
            transform.set("f1db-sqlite.zip")
        }
    }
    release {
        github {
            enabled.set(true)
            repoOwner.set("f1db")
            name.set("f1db")
            username.set("marceloverdijk")
            commitAuthor {
                name.set("F1DB")
                email.set("info@f1db.com")
            }
            skipTag.set(true)
        }
    }
}

tasks.withType<Tar>().configureEach {
    enabled = false
}
