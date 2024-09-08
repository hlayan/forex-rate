plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {

    compileSdk = 34
    namespace = "com.hlayan.forexrate"

    defaultConfig {
        minSdk = 21
        targetSdk = 34
        versionCode = 2
        versionName = "1.1.0"
        applicationId = "com.hlayan.forexrate"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material.icons.extended)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.google.material)
    implementation(libs.google.gson)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.skydoves.sandwich)
    implementation(libs.jakewharton.timber)

    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.converter.gson)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    debugImplementation(libs.androidx.compose.ui.tooling)

    coreLibraryDesugaring(libs.desugar.jdk.libs)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}