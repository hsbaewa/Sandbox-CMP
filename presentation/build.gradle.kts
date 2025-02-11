plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    // kover : coverageReport
    alias(libs.plugins.kover)

    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "presentation"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            // Firebase
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.crashlytics.ktx)
        }
        commonMain.dependencies {
            //put your multiplatform dependencies here

            // data
            implementation(project(":data-preference"))

            // domain
            implementation(project(":domain"))

            // koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            // firebase
            /**
             * ### 참고 ###
             * 공식 github : https://github.com/GitLiveApp/firebase-kotlin-sdk
             * 사용 방법 : https://medium.com/@carlosgub/how-to-implement-firebase-firestore-in-kotlin-multiplatform-mobile-with-compose-multiplatform-32b66cdba9f7
             */
            implementation(libs.kotlin.firebase.analytics)
            implementation(libs.kotlin.firebase.crashlytics)

            // serialization
            implementation(libs.ktor.serialization.kotlinx.json)

            // navigation
            implementation(libs.androidx.navigation)

            // date, time, formatter
            implementation(libs.kotlinx.datetime)

            // material3
            implementation(compose.material3)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "kr.co.hs.sandbox.presentation"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
