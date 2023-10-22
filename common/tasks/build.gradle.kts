plugins {
    id("atomtasks.android.library")
    id("atomtasks.android.library.compose")
    id("atomtasks.detekt")
    id("atomtasks.android.hilt")
}

android {
    namespace = "com.costular.atomtasks.common.tasks"

    defaultConfig {
        testInstrumentationRunner = "com.costular.atomtasks.core.testing.AtomTestRunner"
    }

    packaging {
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.analytics)
    implementation(projects.data)
    implementation(projects.core.notifications)

    implementation(libs.compose.activity)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.accompanist.systemui)
    implementation(libs.viewmodel)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    implementation(libs.work)
    implementation(libs.timber)
    implementation(libs.compose.destinations)
    implementation(libs.accompanist.permissions)
    kapt(libs.hilt.ext.compiler)
    api(libs.reordeable)
    implementation(libs.balloon)

    testImplementation(projects.common.tasks)
    testImplementation(projects.core.testing)
    testImplementation(libs.android.junit)
    testImplementation(libs.compose.ui.test)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.test)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)
    testImplementation(libs.work.testing)

    androidTestImplementation(projects.core.testing)
    androidTestImplementation(libs.android.junit)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.work.testing)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.preferences.datastore)
    androidTestImplementation(libs.hilt.android.testing)

    debugImplementation(libs.compose.ui.manifest)
}
