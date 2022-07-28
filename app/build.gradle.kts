import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt") version "1.20.0-RC1"
    id("org.jetbrains.kotlinx.kover") version "0.5.1"
    id("dagger.hilt.android.plugin")
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
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
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
    implementation(project(":feature-agenda"))
    implementation(project(":feature-create-task"))
    implementation(project(":feature-settings"))

    implementation(libs.fragment)
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.constraintlayout)
    implementation(libs.compose.material.icons)
    implementation(libs.accompanist.systemui)
    implementation(libs.accompanist.insetsui)
    implementation(libs.material)
    implementation(libs.androidx.core)
    implementation(libs.appcompat)
    implementation(libs.lifecycle.runtime)
    implementation(libs.viewmodel)
    implementation(libs.timber)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.jetpack.compiler)
    implementation(libs.hilt.jetpack.viewmodel)
    implementation(libs.hilt.work)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.startup)

    kapt(libs.room.compiler)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.compose.destinations)

    testImplementation(libs.android.junit)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.test)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)
    testImplementation(libs.compose.ui.test)

    androidTestImplementation(project(":core-testing"))
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.work.testing)
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.mockk.android)
    kaptAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.testparameterinjector)
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
