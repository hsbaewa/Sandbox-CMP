plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    // kover : coverageReport
    alias(libs.plugins.kover) apply false
    // Robolectric for instrumentation
    alias(libs.plugins.junit5.robolectric) apply false

    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktorfit) apply false

    // Firebase
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashlytics) apply false

    // Database
    alias(libs.plugins.sqldelight) apply false
}