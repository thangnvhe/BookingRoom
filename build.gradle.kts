// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}
allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.intellij" && requested.name == "annotations") {
                useTarget("org.jetbrains:annotations:23.0.0")
            }
        }
    }
}