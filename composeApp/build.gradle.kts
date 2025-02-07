import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    // kover : coverageReport
    alias(libs.plugins.kover)

    // Firebase
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        // Robolectric for instrumentation
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
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

    // for ios crashlytics
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            freeCompilerArgs += "-Xadd-light-debug=enable"
        }
    }
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> {
            isStatic = true
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // system ui
            implementation(libs.accompanist.systemuicontroller)

            // Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.crashlytics.ktx)

            // koin
            implementation(libs.koin.android)
        }

        androidNativeTest.dependencies {
            // koin
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            // data
            implementation(project(":data"))
            implementation(project(":data-ktor"))
            implementation(project(":data-ktorfit"))
            implementation(project(":data-preference"))
            implementation(project(":data-database"))

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

            // firebase
            /**
             * ### 참고 ###
             * 공식 github : https://github.com/GitLiveApp/firebase-kotlin-sdk
             * 사용 방법 : https://medium.com/@carlosgub/how-to-implement-firebase-firestore-in-kotlin-multiplatform-mobile-with-compose-multiplatform-32b66cdba9f7
             */
            implementation(libs.kotlin.firebase.analytics)
            implementation(libs.kotlin.firebase.crashlytics)
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

        // Robolectric for instrumentation
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Robolectric for instrumentation
    apply(plugin = libs.plugins.junit5.robolectric.get().pluginId)
}

dependencies {
    debugImplementation(compose.uiTooling)

    // Robolectric for instrumentation
    androidTestImplementation(libs.ui.test.junit4.android)
    debugImplementation(libs.ui.test.manifest)
}

/**
 * kover config
 */
dependencies {
    // module - data
    kover(project(":data"))
    kover(project(":data-ktor"))
    kover(project(":data-ktorfit"))
    kover(project(":data-preference"))
    kover(project(":data-database"))

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

// Robolectric for instrumentation : 테스트 코드 동작 시 UsingContext를 단독으로 실행 하다가 충돌이 발생함을 방지.
tasks.withType<Test>().configureEach {
    exclude("kr/co/hs/sandbox/cmp/UsingContext.class")
}
