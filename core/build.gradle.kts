plugins {
    id("atomtasks.android.library")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("kotlin-kapt")
}

android {
    namespace = "com.costular.atomtasks.core"
}

dependencies {
    api(libs.coroutines)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    testImplementation(libs.truth)
}
