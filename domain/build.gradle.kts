plugins {
    id(GradlePlugins.kotlin)
    kotlin("kapt")
}

dependencies {
    implementation(project(":core"))
    implementation("javax.inject:javax.inject:1")
}