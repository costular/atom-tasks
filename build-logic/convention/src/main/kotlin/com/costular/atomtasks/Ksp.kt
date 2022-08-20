package com.costular.atomtasks

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun Project.configureKspLibrary() {
    extensions.configure<LibraryExtension>() {
        libraryVariants.all {
            kotlin.sourceSets {
                getByName(name) {
                    kotlin.srcDir("build/generated/ksp/$name/kotlin")
                }
            }
        }
    }
}

val Project.kotlin: KotlinAndroidProjectExtension
    get() =
        (this as ExtensionAware).extensions.getByName("kotlin") as KotlinAndroidProjectExtension

internal fun KotlinAndroidProjectExtension.sourceSets(
    configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>,
): Unit =
    (this as ExtensionAware).extensions.configure("sourceSets", configure)
