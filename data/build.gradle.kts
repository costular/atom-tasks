plugins {
    id("atomtasks.android.library")
    kotlin("kapt")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    id("atomtasks.android.room")
}

android {
    namespace = "com.costular.atomtasks.data"

    defaultConfig {
        testInstrumentationRunner = "com.costular.atomtasks.core.testing.AtomTestRunner"
    }
}

dependencies {
    implementation(project(":core:ui"))

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.work)
    implementation(libs.preferences.datastore)
    implementation(libs.preferences)
    implementation(libs.hilt.work)
    implementation(libs.timber)

    testImplementation(projects.common.tasks)
    testImplementation(libs.android.junit)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.test)
    testImplementation(libs.mockk)
    testImplementation(libs.testparameterinjector)

    androidTestImplementation(projects.core.testing)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.room.testing)
}
