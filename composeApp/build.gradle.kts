import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    // kover : coverageReport
    alias(libs.plugins.kover)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // system ui
            implementation(libs.accompanist.systemuicontroller)
        }
        commonMain.dependencies {
            // data
            implementation(project(":data"))
            implementation(project(":data-ktor"))
            implementation(project(":data-ktorfit"))

            // domain
            implementation(project(":domain"))

            // material3
            implementation(compose.material3)

            // viewmodel
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            // koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        commonTest.dependencies {
            // koin
            implementation(libs.koin.test)

            // testing
            implementation(kotlin("test"))
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }
}

android {
    namespace = "kr.co.hs.sandbox.cmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "kr.co.hs.sandbox.cmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

/**
 * kover config
 */
dependencies {
    // module - data
    kover(project(":data"))
    kover(project(":data-ktor"))
    kover(project(":data-ktorfit"))

    // module - domain
    kover(project(":domain"))
}

kover {
    reports {
        // filters for all report types of all build variants
        filters {
            excludes {
                androidGeneratedClasses()
                classes(
                    "*.usecase.*UseCase_Factory",

                    "*.di.*_Bind*Factory\$InstanceHolder",
                    "*.di.*_Bind*Factory",

                    "*.BR",
                    "*.*DataBinderMapperImpl",
                    "*.*DataBinderMapperImpl\$InnerBrLookup",
                    "*.*DataBinderMapperImpl\$InnerLayoutIdLookup",
                    "*.DataBindingTriggerClass",
                    "*.*_MembersInjector",
                )
            }
            includes {
                classes(
                    "kr.co.hs.sandbox.*"
                )
            }
        }
        variant("debug") {
            // filters for all report types only for 'release' build variant
            filters {
                excludes {
                    androidGeneratedClasses()
                    classes(
                        "*.usecase.*UseCase_Factory",
                        "*.usecase.*UseCase_Factory\$InstanceHolder",

                        "*.di.*_Bind*Factory\$InstanceHolder",
                        "*.di.*_Bind*Factory",

                        "*.BR",
                        "*.*DataBinderMapperImpl",
                        "*.*DataBinderMapperImpl\$InnerBrLookup",
                        "*.*DataBinderMapperImpl\$InnerLayoutIdLookup",
                        "*.DataBindingTriggerClass",
                        "*.*_MembersInjector",
                    )
                }
                includes {
                    classes(
                        "kr.co.hs.sandbox.*"
                    )
                }

            }
        }


        variant("release") {
            // verification ony for 'release' build variant
            verify {
                rule {
                    minBound(50)
                }
            }

            // filters for all report types only for 'release' build variant
            filters {
                excludes {
                    androidGeneratedClasses()
                    classes(
                        "*.usecase.*UseCase_Factory",
                        "*.usecase.*UseCase_Factory\$InstanceHolder",

                        "*.di.*_Bind*Factory\$InstanceHolder",
                        "*.di.*_Bind*Factory",

                        "*.BR",
                        "*.*DataBinderMapperImpl",
                        "*.*DataBinderMapperImpl\$InnerBrLookup",
                        "*.*DataBinderMapperImpl\$InnerLayoutIdLookup",
                        "*.DataBindingTriggerClass",
                        "*.*_MembersInjector",
                    )
                }
                includes {
                    classes(
                        "kr.co.hs.sandbox.*"
                    )
                }

            }
        }
    }
}
