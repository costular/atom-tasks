buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.androidGradle)
        classpath(libs.kotlinGradle)
        classpath("com.google.dagger:hilt-android-gradle-plugin:${libs.versions.hilt.get()}")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.7")
        classpath(libs.androidx.benchmark.baseline.profile.gradle.plugin)
    }
}
