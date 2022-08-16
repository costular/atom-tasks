plugins {
    id("kotlin")
    kotlin("kapt")
    id("atomtasks.detekt")
    id("atomtasks.ktlint")
}

dependencies {
    implementation(project(":core"))
    implementation("javax.inject:javax.inject:1")
}
