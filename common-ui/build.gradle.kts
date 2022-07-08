plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
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
        kotlinCompilerExtensionVersion = Versions.compose
    }
}

dependencies {
    api(project(":core-ui"))
    implementation(project(":domain"))

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

    implementation(Deps.numberPicker)
    implementation(Deps.lottie)
    implementation(Deps.composeCalendar)
    implementation(Deps.accompanistPager)
    implementation(Deps.accompanistPagerIndicators)

    testImplementation(project(":core-testing"))
}
