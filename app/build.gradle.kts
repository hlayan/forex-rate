plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7" // Support for Kotlin 1.9.21
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.01.00"))

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-extended")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

//    implementation("androidx.compose.material3:material3:1.0.0-alpha14")
//    implementation("androidx.navigation:navigation-compose:2.5.2")
//    implementation("com.google.accompanist:accompanist-pager:0.19.0")

    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    implementation("com.github.skydoves:sandwich:1.3.2")
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    debugImplementation("androidx.compose.ui:ui-tooling")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}