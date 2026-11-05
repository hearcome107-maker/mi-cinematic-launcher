plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.siddesh.micinema"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.siddesh.micinema"
        minSdk = 28
        targetSdk = 28
        versionCode = 1
        versionName = "0.1.0"
    }

    buildFeatures { viewBinding = true }
}
