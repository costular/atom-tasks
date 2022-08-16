plugins {
    `kotlin-dsl`
}

group = "com.costular.atomtasks.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.androidGradle)
    implementation(libs.kotlinGradle)
    implementation(libs.ktlintGradle)
    implementation(libs.detektGradle)
}

gradlePlugin {
    plugins {
        register("ktlint") {
            id = "atomtasks.ktlint"
            implementationClass = "KtlintConventionPlugin"
        }
        register("androidApplication") {
            id = "atomtasks.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidFeature") {
            id = "atomtasks.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibrary") {
            id = "atomtasks.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "atomtasks.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibraryKsp") {
            id = "atomtasks.android.library.ksp"
            implementationClass = "AndroidLibraryKspConventionPlugin"
        }
        register("detekt") {
            id = "atomtasks.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("jacoco") {
            id = "atomtasks.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
    }
}
