plugins {
    id("atomtasks.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    implementation(libs.hilt)
    implementation(libs.hilt.android.testing)
    implementation(libs.androidx.test.runner)
    kapt(libs.hilt.compiler)

    api(libs.android.junit)
    api(libs.junit)
    api(libs.coroutines.test)
    api(libs.turbine)
    api(libs.truth)
    api(libs.androidx.test)
    api(libs.mockk)
    api(libs.robolectric)
    api(libs.compose.ui.test)
    debugApi(libs.compose.ui.manifest)
}
