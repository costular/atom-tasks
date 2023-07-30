plugins {
    id("atomtasks.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.costular.atomtasks.core.testing"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    implementation(libs.hilt)
    implementation(libs.hilt.android.testing)
    implementation(libs.androidx.test.runner)
    kapt(libs.hilt.compiler)

    implementation(libs.android.junit)
    implementation(libs.junit)
    implementation(libs.coroutines.test)
    implementation(libs.androidx.test)
    implementation(libs.compose.ui.test)
}
