plugins {
    id("atomtasks.android.library")
    id("atomtasks.detekt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.costular.atomtasks.core"
}

dependencies {
    api(libs.coroutines)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.truth)
}
