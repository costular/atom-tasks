plugins {
    id("atomtasks.android.library")
    alias(libs.plugins.ksp)
    kotlin("kapt")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }

        testInstrumentationRunner = "com.costular.atomtasks.coretesting.AtomTestRunner"
    }
}

dependencies {
    implementation(project(":core-ui"))

    api(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.work)
    implementation(libs.preferences.datastore)
    implementation(libs.preferences)
    implementation(libs.hilt.work)
    implementation(libs.timber)

    testImplementation(libs.android.junit)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.test)
    testImplementation(libs.mockk)

    androidTestImplementation(projects.coreTesting)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.androidx.test)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.hilt.android.testing)
}
