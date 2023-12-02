enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
include(":core")
include(":core:designsystem")
include(":core:testing")
include(":core:ui")
include(":common:tasks")
include(":data")
include(":feature:settings")
include(":feature:agenda")
include(":feature:createtask")
include(":feature:edittask")
include(":screenshot_testing")
include(":core:analytics")
include(":feature:postpone-task")
include(":core:notifications")
include(":core:logging")
include(":baselineprofile")
include(":core:review")
include(":core:preferences")
