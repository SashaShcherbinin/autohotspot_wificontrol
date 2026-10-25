import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

val localProperties = Properties().apply {
    FileInputStream(rootProject.file("local.properties")).use { load(it) }
}

val localStoreFile: String = localProperties.getProperty("storeFile")
val localStorePassword: String = localProperties.getProperty("storePassword")
val localKeyAlias: String = localProperties.getProperty("keyAlias")
val localKeyPassword: String = localProperties.getProperty("keyPassword")

android {
    namespace = "wifi.control"
    compileSdk = 34

    signingConfigs {
        create("release") {
            this.storeFile = file(localStoreFile)
            this.storePassword = localStorePassword
            this.keyAlias = localKeyAlias
            this.keyPassword = localKeyPassword
        }
        getByName("debug") {
            this.storeFile = file(localStoreFile)
            this.storePassword = localStorePassword
            this.keyAlias = localKeyAlias
            this.keyPassword = localKeyPassword
        }
    }

    defaultConfig {
        applicationId = "wifi.auto.hotspot.wifi.control.provider.content"
        minSdk = 26
        targetSdk = 28
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    lint.abortOnError = false

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
}