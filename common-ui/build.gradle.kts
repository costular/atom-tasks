plugins {
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("kotlin-android")
    kotlin("kapt")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
}

dependencies {
    api(project(":core-ui"))
    implementation(projects.data)

    implementation(libs.compose.activity)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.accompanist.systemui)
    implementation(libs.accompanist.insetsui)
    implementation(libs.viewmodel)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.androidx.compiler)

    implementation(libs.numberpicker)
    implementation(libs.lottie)
    implementation(libs.composecalendar)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    testImplementation(project(":core-testing"))
}
