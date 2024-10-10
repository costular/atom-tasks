plugins {
    id("atomtasks.android.library")
    id("kotlin-android")
    id("atomtasks.detekt")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.costular.atomtasks.core.analytics"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(projects.core.testing)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.robolectric)
}
