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

android {
    defaultConfig {
        testInstrumentationRunner = "com.costular.atomtasks.coretesting.AtomTestRunner"
    }
}

dependencies {
    api(project(":core-ui"))
    implementation(projects.data)

    implementation(libs.compose.activity)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.accompanist.systemui)
    implementation(libs.viewmodel)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.androidx.compiler)

    implementation(libs.numberpicker)
    implementation(libs.calendar)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    testImplementation(projects.coreTesting)
    testImplementation(libs.android.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)

    testImplementation(projects.coreTesting)
    androidTestImplementation(projects.coreTesting)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.mockk.android)
    kaptAndroidTest(libs.hilt.compiler)
    debugImplementation(libs.compose.ui.manifest)
}