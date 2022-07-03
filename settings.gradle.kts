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
include(":settings")
