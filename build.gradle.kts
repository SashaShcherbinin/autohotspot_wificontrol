import java.io.FileInputStream
import java.util.Properties

plugins {
    id(libs.plugins.common.android.application.module)
}

val localProperties = Properties().apply {
    FileInputStream(rootProject.file("./signing.properties")).use { load(it) }
}

val localStoreFile: String = localProperties.getProperty("storeFile")
val localStorePassword: String = localProperties.getProperty("storePassword")
val localKeyAlias: String = localProperties.getProperty("keyAlias")
val localKeyPassword: String = localProperties.getProperty("keyPassword")

android {
    namespace = "wifi.control"
    //noinspection GradleDependency - we need to support older devices
    compileSdk = 28

    signingConfigs {
        create("release") {
            this.storeFile = file(localStoreFile)
            this.storePassword = localStorePassword
            this.keyAlias = localKeyAlias
            this.keyPassword = localKeyPassword
        }
    }

    defaultConfig {
        applicationId = "wifi.auto.hotspot.wifi.control.provider.content"
        minSdk = 24
        //noinspection ExpiredTargetSdkVersion - we need to support older devices
        targetSdk = 28
        versionCode = 5
        versionName = "1.1.3"
    }

    lint.abortOnError = false

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
}