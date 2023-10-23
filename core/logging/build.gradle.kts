plugins {
    id("atomtasks.android.library")
    id("atomtasks.detekt")
    id("kotlin-kapt")
}

android {
    namespace = "com.costular.atomtasks.core.logger.firebase"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
}
