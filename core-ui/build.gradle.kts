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
    api(project(":core"))

    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
    implementation(Deps.core)
    implementation(Deps.composeRuntime)
    implementation(Deps.composeUi)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeDestinations)

    implementation(Deps.lifecycleRuntimeKtx)
    implementation(Deps.hiltNavigationCompose)
    implementation(Deps.viewModel)

    testImplementation(Deps.androidJunit)
    testImplementation(Deps.junit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.turbine)
    testImplementation(Deps.truth)
    testImplementation(Deps.test)
    testImplementation(Deps.mockk)
    testImplementation(Deps.composeUiTest)
}
