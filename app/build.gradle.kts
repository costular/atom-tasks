import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(GradlePlugins.android)
    kotlin("android")
    kotlin("kapt")
    id(GradlePlugins.kotlinParcelize)
    id(GradlePlugins.hilt)
    id("com.github.ben-manes.versions") version "0.39.0"
}

android {
    compileSdk = Config.compileVersion
    defaultConfig {
        applicationId = "com.costular.atomtasks"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
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

    packagingOptions {
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.fragment)
    implementation(Deps.hilt)
    implementation(Deps.constraintLayout)
    implementation(Deps.material)
    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.lifecycleRuntimeKtx)
    implementation(Deps.viewModel)
    implementation(Deps.coroutines)
    implementation(Deps.kotlin)
    implementation(Deps.timber)
    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltJetpackCompiler)
    implementation(Deps.hiltJetpackViewModel)
    implementation(Deps.appInitializer)
    implementation(Deps.preferences)
    implementation(Deps.preferencesDataStore)
    implementation(Deps.hiltWork)
    implementation(Deps.composeActivity)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeRuntime)
    implementation(Deps.composeLayout)
    implementation(Deps.composeMaterial)
    implementation(Deps.composeMaterialIcons)
    implementation(Deps.composeUi)
    implementation(Deps.composeUiTooling)
    implementation(Deps.accompanistCoil)
    implementation(Deps.hiltNavigationCompose)
    implementation(Deps.workManager)
    implementation(Deps.roomKtx)
    implementation(Deps.roomRuntinme)
    implementation(Deps.accompanistPager)
    implementation(Deps.accompanistPagerIndicators)
    implementation(Deps.materialDialogsDatetime)
    implementation(Deps.composeCalendar)
    kapt(Deps.roomCompiler)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation(Deps.numberPicker)
    implementation(Deps.lottie)

    testImplementation(Deps.androidJunit)
    testImplementation(Deps.junit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.turbine)
    testImplementation(Deps.truth)
    testImplementation(Deps.test)
    testImplementation(Deps.mockk)

    androidTestImplementation(Deps.androidJunit)
    androidTestImplementation(Deps.coroutinesTest)
    androidTestImplementation(Deps.truth)
    androidTestImplementation(Deps.turbine)
    androidTestImplementation(Deps.androidTestRunner)
    androidTestImplementation(Deps.androidTestRules)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xinline-classes"
}