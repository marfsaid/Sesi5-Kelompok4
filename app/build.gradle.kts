plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // tanpa versi di modul
}

android {
    namespace = "com.example.sesi5kelompok4"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sesi5kelompok4"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    // gunakan versi compiler extension yang cocok dengan versi Compose di projectmu
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    kotlinOptions {
        // target JVM sesuai screenshot: 1.8
        jvmTarget = "1.8"

        // tambahkan argumen compiler untuk bahasa/API versi 2.1 (sesuai screenshot IDE)
        freeCompilerArgs = listOf(
            "-Xjsr305=strict",
            "-language-version=2.1",
            "-api-version=2.1"
            // jika perlu opt-in experimental: "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    packaging {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    // BOM (biar versi compose seragam)
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))

    // UI core
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Material (stable) - sesuai versi awal yang kita gunakan
    implementation("androidx.compose.material:material")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.0")

    // Foundation
    implementation("androidx.compose.foundation:foundation")

    // Icons (opsional)
    implementation("androidx.compose.material:material-icons-extended")

    // Core KTX
    implementation("androidx.core:core-ktx:1.12.0")

    // Debug tooling
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Testing
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.0") // opsional

}
