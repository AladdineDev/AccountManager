// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val roomVersion = "2.6.1"
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("androidx.room") version roomVersion apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
    alias(libs.plugins.compose.compiler) apply false
}