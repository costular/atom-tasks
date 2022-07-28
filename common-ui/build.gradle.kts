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
    api(project(":core-ui"))
    implementation(project(":domain"))

    implementation(libs.compose.activity)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.accompanist.systemui)
    implementation(libs.accompanist.insetsui)
    implementation(libs.viewmodel)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.jetpack.viewmodel)
    kapt(libs.hilt.jetpack.compiler)

    implementation(libs.numberpicker)
    implementation(libs.lottie)
    implementation(libs.composecalendar)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    testImplementation(project(":core-testing"))
}
