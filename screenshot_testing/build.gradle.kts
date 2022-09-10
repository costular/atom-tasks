plugins {
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("atomtasks.ktlint")
    id("atomtasks.detekt")
    alias(libs.plugins.paparazzi)
}

androidComponents {
    // Disable release builds for this test-only library, no need to run screenshot tests more than
    // once
    beforeVariants(selector().withBuildType("release")) { builder ->
        builder.enable = false
    }
}

dependencies {
    implementation(projects.commonUi)

    testImplementation(projects.data)
    testImplementation(libs.compose.ui)
    testImplementation(libs.compose.runtime)
    testImplementation(libs.compose.material3)
    testImplementation(libs.compose.material.icons)
    testImplementation(libs.testparameterinjector)
}

tasks.named("check") {
    dependsOn("verifyPaparazziDemoDebug")
}

tasks.withType<Test>().configureEach {
    // Increase memory for Paparazzi tests
    maxHeapSize = "2g"
}
