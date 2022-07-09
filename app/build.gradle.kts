import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(GradlePlugins.android)
    kotlin("android")
    kotlin("kapt")
    id(GradlePlugins.kotlinParcelize)
    id(GradlePlugins.hilt)
    id("com.github.ben-manes.versions") version "0.39.0"
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt") version "1.20.0-RC1"
    id("org.jetbrains.kotlinx.kover") version "0.5.1"
}

android {
    compileSdk = Config.compileVersion
    defaultConfig {
        applicationId = "com.costular.atomtasks"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "com.costular.atomtasks.di.AtomHiltRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }

        packagingOptions {
            // for JNA and JNA-platform
            resources.excludes.add("META-INF/AL2.0")
            resources.excludes.add("META-INF/LGPL2.1")
            // for byte-buddy
            resources.excludes.add("META-INF/licenses/ASM")
            resources.pickFirsts.add("win32-x86-64/attach_hotspot_windows.dll")
            resources.pickFirsts.add("win32-x86/attach_hotspot_windows.dll")
        }
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("release.keystore")
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

kapt {
    correctErrorTypes = true
}

tasks.koverHtmlReport {
    excludes = listOf(
        "*.R.class",
        "*.R$*.class",
        "*.Manifest*.*",
        "android.*.*.*",
        "*.BuildConfig.*",
        "*.*Module.*",
        "*Hilt*",
        "*.*_MembersInjector",
        "*.*_HiltComponents*",
        "*.*_ComponentTreeDeps*",
        "*.*Destination",
        "*.*Dao*",
        "*.*Factory*",
        "*.*Activity*",
    )
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":common-ui"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":agenda"))
    implementation(project(":feature-create-task"))
    implementation(project(":settings"))

    implementation(Deps.fragment)
    implementation(Deps.composeUi)
    implementation(Deps.composeMaterial)
    implementation(Deps.constraintLayout)
    implementation(Deps.composeMaterialIcons)
    implementation(Deps.accompanistSystemUi)
    implementation(Deps.accompanistInsetsUi)
    implementation(Deps.material)
    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.lifecycleRuntimeKtx)
    implementation(Deps.viewModel)
    implementation(Deps.timber)
    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)
    kapt(Deps.hiltJetpackCompiler)
    implementation(Deps.hiltJetpackViewModel)
    implementation(Deps.hiltWork)
    implementation(Deps.hiltNavigationCompose)
    implementation(Deps.appInitializer)

    kapt(Deps.roomCompiler)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation(platform(Deps.firebaseBom))
    implementation(Deps.firebaseAnalytics)
    implementation(Deps.firebaseCrashlytics)
    implementation(Deps.composeDestinations)

    testImplementation(Deps.androidJunit)
    testImplementation(Deps.junit)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.turbine)
    testImplementation(Deps.truth)
    testImplementation(Deps.test)
    testImplementation(Deps.mockk)
    testImplementation(Deps.robolectric)
    testImplementation(Deps.composeUiTest)

    androidTestImplementation(project(":core-testing"))
    androidTestImplementation(Deps.androidJunit)
    androidTestImplementation(Deps.coroutinesTest)
    androidTestImplementation(Deps.truth)
    androidTestImplementation(Deps.turbine)
    androidTestImplementation(Deps.androidTestRunner)
    androidTestImplementation(Deps.androidTestRules)
    androidTestImplementation(Deps.workManagerTesting)
    androidTestImplementation(Deps.composeUiTest)
    androidTestImplementation(Deps.hiltAndroidTesting)
    androidTestImplementation(Deps.mockkAndroid)
    kaptAndroidTest(Deps.hiltCompiler)
    androidTestImplementation(Deps.testParameterInjector)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs +=
        listOf(
            "-Xinline-classes",
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=kotlinx.coroutines.InternalCoroutinesApi",
            "-Xuse-experimental=androidx.compose.animation.ExperimentalAnimationApi",
            "-Xuse-experimental=androidx.compose.ExperimentalComposeApi",
            "-Xuse-experimental=androidx.compose.material.ExperimentalMaterialApi",
            "-Xuse-experimental=androidx.compose.runtime.ExperimentalComposeApi",
            "-Xuse-experimental=androidx.compose.ui.ExperimentalComposeUiApi",
            "-Xuse-experimental=coil.annotation.ExperimentalCoilApi",
            "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
            "-Xuse-experimental=com.google.accompanist.pager.ExperimentalPagerApi",
            "-Xuse-experimental=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
        )
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config =
        files("$projectDir/config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
