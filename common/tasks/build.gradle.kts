plugins {
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("atomtasks.ktlint")
    id("atomtasks.detekt")
    kotlin("kapt")
}

dependencies {
    implementation(projects.commonUi)
    implementation(projects.data)

    implementation(libs.compose.activity)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.accompanist.systemui)
    implementation(libs.accompanist.insetsui)
    implementation(libs.viewmodel)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.lottie)
    implementation(libs.work)
    implementation(libs.hilt.work)
    kapt(libs.hilt.androidx.compiler)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.compose.destinations)

    testImplementation(projects.common.tasks)
    testImplementation(libs.android.junit)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.test)
    testImplementation(libs.mockk)
}