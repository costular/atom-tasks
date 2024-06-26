package com.costular.atomtasks

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

enum class FlavorDimension(
    val naming: String
) {
    Environment("environment")
}

enum class AtomFlavor(
    val naming: String,
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
) {
    Development("development", FlavorDimension.Environment, applicationIdSuffix = ".dev"),
    Production("production", FlavorDimension.Environment),
}

enum class AtomBuildType(val applicationIdSuffix: String? = null) {
    DEBUG,
    RELEASE,
    BENCHMARK(".benchmark")
}

fun configureFlavors(
    commonExtensions: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(atomFlavor: AtomFlavor) -> Unit = {},
) {
    commonExtensions.apply {
        flavorDimensions += FlavorDimension.Environment.naming
        productFlavors {
            AtomFlavor.values().forEach { flavor ->
                create(flavor.naming) {
                    dimension = flavor.dimension.naming
                    flavorConfigurationBlock(this, flavor)
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
    with(baseAppModuleExtension) {
        buildTypes {
            val release = getByName("release") {
                isDebuggable = false
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                applicationIdSuffix = AtomBuildType.RELEASE.applicationIdSuffix
            }
            getByName("debug") {
                isDebuggable = true
                isMinifyEnabled = false
                isShrinkResources = false
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                applicationIdSuffix = AtomBuildType.DEBUG.applicationIdSuffix
            }
            create("benchmark") {
                initWith(release)
                matchingFallbacks.add("release")
                proguardFiles("benchmark-rules.pro")
                isMinifyEnabled = true
                applicationIdSuffix = AtomBuildType.BENCHMARK.applicationIdSuffix
            }
        }
    }
}
