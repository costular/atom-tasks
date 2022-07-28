plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.6.10-1.0.4"
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
    implementation(project(":core-ui"))
    api(project(":domain"))

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
}
