import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.costular.atomtasks.configureAndroidCompose
import com.costular.atomtasks.configureBuildTypes
import com.costular.atomtasks.configureFlavors
import com.costular.atomtasks.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this)
                configureBuildTypes(this)
                configureAndroidCompose(this)
                defaultConfig.targetSdk = 34
            }
        }
    }
}
