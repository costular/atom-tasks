package com.costular.atomtasks

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

enum class FlavorDimension(
    val naming: String
) {
    Environment("environment")
}

enum class Flavor(
    val naming: String,
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
) {
    Development("development", FlavorDimension.Environment, applicationIdSuffix = ".dev"),
    Production("production", FlavorDimension.Environment)
}

fun configureFlavors(commonExtensions: CommonExtension<*, *, *, *, *>) {
    commonExtensions.apply {
        flavorDimensions += FlavorDimension.Environment.naming
        productFlavors {
            Flavor.values().forEach { flavor ->
                create(flavor.naming) {
                    dimension = flavor.dimension.naming
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        flavor.applicationIdSuffix?.let {
                            applicationIdSuffix = it
                        }
                    }
                }
            }
        }
    }
}

fun configureBuildTypes(baseAppModuleExtension: BaseAppModuleExtension) {
    with (baseAppModuleExtension) {
        buildTypes {
            getByName("release") {
                isDebuggable = false
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
            getByName("debug") {
                isDebuggable = true
                isMinifyEnabled = false
                isShrinkResources = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }
    }
}
