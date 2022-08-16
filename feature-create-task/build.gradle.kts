plugins {
    id("atomtasks.android.feature")
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("atomtasks.android.library.ksp")
    id("kotlin-android")
    kotlin("kapt")
    alias(libs.plugins.ksp)
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
    id("atomtasks.android.library.jacoco")
}

android {
    ksp {
        arg("compose-destinations.mode", "destinations")
        arg("compose-destinations.moduleName", "createtask")
    }
    libraryVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)

    implementation(libs.numberpicker)
    implementation(libs.lottie)
    implementation(libs.composecalendar)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
}
