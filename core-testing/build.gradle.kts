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
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)

    api(Deps.androidJunit)
    api(Deps.junit)
    api(Deps.coroutinesTest)
    api(Deps.turbine)
    api(Deps.truth)
    api(Deps.test)
    api(Deps.mockk)
    api(Deps.robolectric)
    api(Deps.composeUiTest)
    debugApi(Deps.composeUiManifest)
}
