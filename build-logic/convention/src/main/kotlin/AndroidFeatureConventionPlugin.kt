import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import com.android.build.gradle.LibraryExtension

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }
            extensions.configure<LibraryExtension>() {
                defaultConfig {
                    testInstrumentationRunner = "com.costular.atomtasks.core.testing.AtomTestRunner"
                }

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":data"))
                add("implementation", libs.findLibrary("compose.activity").get())
                add("implementation", libs.findLibrary("compose.foundation").get())
                add("implementation", libs.findLibrary("compose.runtime").get())
                add("implementation", libs.findLibrary("compose.layout").get())
                add("implementation", libs.findLibrary("compose.material3").get())
                add("implementation", libs.findLibrary("compose.material3.windowsize").get())
                add("implementation", libs.findLibrary("compose.material.icons").get())
                add("implementation", libs.findLibrary("compose.ui").get())
                add("implementation", libs.findLibrary("compose.ui.tooling").get())
                add("implementation", libs.findLibrary("accompanist.systemui").get())
                add("implementation", libs.findLibrary("viewmodel").get())
                add("implementation", libs.findLibrary("hilt.navigation.compose").get())
                add("kapt", libs.findLibrary("hilt.androidx.compiler").get())
                add("implementation", libs.findLibrary("hilt").get())
                add("kapt", libs.findLibrary("hilt.compiler").get())
                add("implementation", libs.findLibrary("compose.destinations").get())

                add("testImplementation", project(":core:testing"))
            }
        }
    }
}
