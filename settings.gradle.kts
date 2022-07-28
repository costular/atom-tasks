pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AtomReminders"
include(":app")
include(":common-ui")
include(":core")
include(":data")
include(":core-ui")
include(":domain")
include(":feature-settings")
include(":feature-agenda")
include(":core-testing")
include(":feature-create-task")
