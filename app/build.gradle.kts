plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // <--- DODAJ TO!
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aistudio.fleetmanager.mvp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ... (reszta kodu z signingConfigs, buildTypes, itp. bez zmian) ...
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    kotlinOptions {
        jvmTarget = "11"
    }
    
    buildFeatures {
        compose = true
        buildConfig = true
    }
    
    // BLOK composeOptions ZOSTAŁ CAŁKOWICIE USUNIĘTY
}
