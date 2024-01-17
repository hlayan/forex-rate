// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.2.1" apply false
    kotlin("android") version "1.9.21" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
    }
}
