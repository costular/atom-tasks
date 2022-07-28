plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
}

android {
    compileSdk = Config.compileVersion
    defaultConfig {
        minSdk = Config.minSdk
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    implementation(libs.hilt)
    implementation(libs.hilt.android.testing)
    kapt(libs.hilt.compiler)

    api(libs.android.junit)
    api(libs.junit)
    api(libs.coroutines.test)
    api(libs.turbine)
    api(libs.truth)
    api(libs.androidx.test)
    api(libs.mockk)
    api(libs.robolectric)
    api(libs.compose.ui.test)
    debugApi(libs.compose.ui.manifest)
}
