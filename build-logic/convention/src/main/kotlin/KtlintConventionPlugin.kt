import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with (target) {
            pluginManager.apply("org.jlleitschuh.gradle.ktlint")

            extensions.configure<KtlintExtension> {
                version.set("0.45.1")
                android.set(true)
                outputToConsole.set(true)
                outputColorName.set("RED")
                enableExperimentalRules.set(true)
                filter {
                    exclude("**/generated/**")
                    exclude("**.gradle.kts")
                    include("**/kotlin/**")
                }
            }
        }
    }
}
