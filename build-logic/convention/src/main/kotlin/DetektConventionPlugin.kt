import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            extensions.configure<DetektExtension> {
                parallel = true
                buildUponDefaultConfig = true // preconfigure defaults
                allRules = false // activate all available (even unstable) rules.
                config = files("$projectDir/config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
            }

            tasks.withType<Detekt>().configureEach {
                reports {
                    html.required.set(true) // observe findings in your browser with structure and code snippets
                    xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
                    txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
                    sarif.required.set(true) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
                }
            }

            tasks.withType<Detekt>().configureEach {
                jvmTarget = "1.8"
            }
            tasks.withType<DetektCreateBaselineTask>().configureEach {
                jvmTarget = "1.8"
            }
        }
    }
}
