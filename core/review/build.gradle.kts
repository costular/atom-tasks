plugins {
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("kotlin-android")
    id("atomtasks.detekt")
    id("atomtasks.android.library.jacoco")
}

android {
    namespace = "com.costular.atomtasks.core.ui"
}

dependencies {
    api(project(":core"))
    api(libs.compose.bom)
    api(libs.lifecycle.compose)

    implementation(libs.androidx.core)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.viewmodel)

    testImplementation(libs.android.junit)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.test)
    testImplementation(libs.mockk)
    testImplementation(libs.compose.ui.test)
}
