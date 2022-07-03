plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
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
        kotlinCompilerExtensionVersion = Versions.compose
    }

    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "settings")
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

    implementation(Deps.composeActivity)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeRuntime)
    implementation(Deps.composeLayout)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeMaterialIcons)
    implementation(Deps.composeUi)
    implementation(Deps.composeUiTooling)
    implementation(Deps.accompanistSystemUi)
    implementation(Deps.accompanistInsetsUi)
    implementation(Deps.viewModel)
    implementation(Deps.hiltNavigationCompose)
    implementation(Deps.hiltJetpackViewModel)
    kapt(Deps.hiltJetpackCompiler)
    implementation(Deps.composeDestinations)
    ksp(Deps.composeDestinationsKsp)

    implementation(Deps.numberPicker)
    implementation(Deps.lottie)
    implementation(Deps.composeCalendar)
    implementation(Deps.accompanistPager)
    implementation(Deps.accompanistPagerIndicators)
}
