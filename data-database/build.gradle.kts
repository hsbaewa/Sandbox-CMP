plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    // kover : coverageReport
    alias(libs.plugins.kover)

    // Database
    alias(libs.plugins.sqldelight)
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
            baseName = "data-database"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            //put your multiplatform dependencies here

            // koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            // Database
            implementation(libs.sqldelight.runtime)

            implementation(project(":domain"))

            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // Database
            implementation(libs.sqldelight.android.driver)
        }

        iosMain.dependencies {
            // Database
            implementation(libs.sqldelight.native.driver)
        }
    }
}

android {
    namespace = "kr.co.hs.sandbox.data.database"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("kr.co.hs.sandbox.data.database.cache")
        }
    }
}
