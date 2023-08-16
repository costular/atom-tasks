plugins {
    id("atomtasks.android.application")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("jacoco")
}

android {
    namespace = "com.costular.atomtasks"

    defaultConfig {
        applicationId = "com.costular.atomtasks"
        versionCode = 9
        versionName = "0.8.0"
        testInstrumentationRunner = "com.costular.atomtasks.core.testing.AtomTestRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["dagger.hilt.disableModulesHaveInstallInCheck"] = "true"
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }

        packaging {
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

configurations {
    androidTestImplementation {
        exclude(group ="io.mockk", module= "mockk-agent-jvm")
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":data"))
    implementation(project(":feature:agenda"))
    implementation(project(":feature:createtask"))
    implementation(project(":feature:settings"))
    implementation(projects.common.tasks)
    implementation(projects.feature.edittask)

    implementation(libs.fragment)
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.compose.material.icons)
    implementation(libs.accompanist.systemui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.androidx.core)
    implementation(libs.appcompat)
    implementation(libs.lifecycle.runtime)
    implementation(libs.viewmodel)
    implementation(libs.timber)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.androidx.compiler)
    implementation(libs.hilt.work)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.startup)
    implementation(libs.work)

    kapt(libs.room.compiler)
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
    testImplementation(libs.compose.ui.test)
}
