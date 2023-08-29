plugins {
    `kotlin-dsl`
}

group = "com.costular.atomtasks.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.androidGradle)
    compileOnly(libs.kotlinGradle)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.detektGradle)
}

gradlePlugin {
    plugins {
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
        register("androidRoom") {
            id = "atomtasks.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}
