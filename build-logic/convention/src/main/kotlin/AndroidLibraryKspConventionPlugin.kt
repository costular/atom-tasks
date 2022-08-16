import com.costular.atomtasks.configureKspLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryKspConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with (target) {
            configureKspLibrary()
        }
    }
}
