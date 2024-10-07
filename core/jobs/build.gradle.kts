plugins {
    id("atomtasks.android.library")
    id("kotlin-android")
    id("atomtasks.detekt")
    id("atomtasks.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.costular.atomtasks.core.jobs"
}

dependencies {
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.work)

    testImplementation(libs.work.testing)
}
