plugins {
    id("atomtasks.android.feature")
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("atomtasks.android.library.ksp")
    id("kotlin-android")
    kotlin("kapt")
    alias(libs.plugins.ksp)
    id("atomtasks.detekt")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.costular.atomtasks.feature.detail"

    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "detail")
    }
    libraryVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(projects.common.tasks)
    implementation(projects.core.analytics)
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.accompanist.permissions)

    testImplementation(projects.core.testing)
    testImplementation(libs.android.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.testparameterinjector)
}
