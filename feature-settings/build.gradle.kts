plugins {
    id("atomtasks.android.feature")
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("atomtasks.android.library.ksp")
    id("kotlin-android")
    kotlin("kapt")
    alias(libs.plugins.ksp)
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
}

android {
    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "settings")
    }
}

dependencies {
    kapt(libs.hilt.androidx.compiler)
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)

    implementation(libs.numberpicker)
    implementation(libs.lottie)
    implementation(libs.composecalendar)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    testImplementation(projects.coreTesting)
    testImplementation(projects.coreUi)
    testImplementation(libs.android.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)
    testImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.manifest)
}
