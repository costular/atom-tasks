plugins {
    `kotlin-dsl`
}

group = "com.costular.atomtasks.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.ktlintGradle)
}

gradlePlugin {
    plugins {
        register("ktlint") {
            id = "atomtasks.ktlint"
            implementationClass = "KtlintConventionPlugin"
        }
    }
}
