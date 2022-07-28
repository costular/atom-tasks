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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies {
    api(project(":core"))

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.core)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.compose.destinations)

    implementation(libs.lifecycle.runtime)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.viewmodel)

    testImplementation(libs.android.junit)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.test)
    testImplementation(libs.mockk)
    testImplementation(libs.compose.ui.test)
}
