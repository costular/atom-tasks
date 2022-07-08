plugins {
    id(GradlePlugins.androidLibrary)
    id(GradlePlugins.kotlinAndroid)
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

    api(Deps.roomRuntinme)
    implementation(Deps.roomKtx)
    ksp(Deps.roomCompiler)

    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
    implementation(Deps.workManager)
    implementation(Deps.preferencesDataStore)
    implementation(Deps.preferences)
    implementation(Deps.hiltWork)
    implementation(Deps.timber)

    testImplementation(Deps.androidJunit)
    testImplementation(Deps.junit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.turbine)
    testImplementation(Deps.truth)
    testImplementation(Deps.test)
    testImplementation(Deps.mockk)
}
