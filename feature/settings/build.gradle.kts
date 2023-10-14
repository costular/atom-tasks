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
    namespace = "com.costular.atomtasks.feature.settings"

    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "settings")
    }
}

dependencies {
    implementation(projects.core.analytics)
    implementation(project(mapOf("path" to ":feature:agenda")))
    kapt(libs.hilt.compiler)
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.compose.ui.manifest)
    implementation(libs.calendar)

    testImplementation(projects.core.testing)
    testImplementation(projects.core.ui)
    testImplementation(libs.android.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)
    testImplementation(libs.compose.ui.test)
}
