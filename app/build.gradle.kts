plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

val env: MutableMap<String, String> = System.getenv()

android {
    compileSdk = 32
    namespace = "com.hlayan.forexrate"

    defaultConfig {
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0.0"
        applicationId = "com.hlayan.forexrate"
        vectorDrawables.useSupportLibrary = true
    }

    val releaseSigningConfig = signingConfigs.create("release") {
        storeFile = File(env["ReleaseStoreFile"]!!)
        storePassword = env["ReleaseStorePassword"]
        keyAlias = env["ReleaseKeyAlias"]
        keyPassword = env["ReleaseKeyPassword"]
    }

    buildTypes {
        getByName("debug") {
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = releaseSigningConfig
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        useLiveLiterals = false
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")

    val composeVersion = "1.1.1"
    implementation("androidx.compose.ui:ui:1.3.0-alpha01")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
//    implementation("androidx.compose.material3:material3:1.0.0-alpha14")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.activity:activity-compose:1.5.0")

    implementation("androidx.core:core-splashscreen:1.0.0-rc01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")
    implementation("androidx.navigation:navigation-compose:2.5.0")

    implementation("com.google.android.material:material:1.6.1")
    implementation("com.google.accompanist:accompanist-pager:0.19.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.17.0")

    implementation("com.github.skydoves:sandwich:1.2.4")
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.google.dagger:hilt-android:2.41")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}