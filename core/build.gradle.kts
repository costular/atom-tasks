plugins {
    id("atomtasks.android.library")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("kotlin-kapt")
}

dependencies {
    api(libs.coroutines)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    testImplementation(libs.truth)
}
