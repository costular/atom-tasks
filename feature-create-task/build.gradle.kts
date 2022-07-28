plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("com.google.devtools.ksp") version "1.6.10-1.0.4"
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

    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "createtask")
    }
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

dependencies {
    implementation(project(":common-ui"))
    implementation(project(":data"))

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
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.numberpicker)
    implementation(libs.lottie)
    implementation(libs.composecalendar)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    testImplementation(project(":core-testing"))
}
