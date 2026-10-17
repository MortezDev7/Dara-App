
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.baselineprofile)
}

val localProperties = Properties().apply {
    val propertiesFile = rootProject.file("local.properties")

    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { load(it) }
    }
}

val brsApiKey = localProperties.getProperty(
    "BRS_API_KEY",
    ""
)

val serverBaseUrl = localProperties.getProperty(
    "SERVER_BASE_URL",
    "https://example.invalid/"
)

android {
    namespace = "com.x7.dara"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.x7.dara"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "BRS_API_KEY",
            "\"$brsApiKey\""
        )

        buildConfigField(
            "String",
            "SERVER_BASE_URL",
            "\"$serverBaseUrl\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }

        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    experimentalProperties[
        "android.experimental.self-instrumenting"
    ] = true
}

baselineProfile {
    variants.configureEach {
        automaticGenerationDuringBuild = true
    }
}

dependencies {
    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Compose
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.icons.extended)

    // ProfileInstaller
    implementation(libs.androidx.profileinstaller)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // Dagger Hilt
    implementation(libs.dagger)
    implementation(libs.dagger.android)
    "baselineProfile"(project(":baselineprofile"))
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.metadata.jvm)
    implementation(libs.hilt.navigation.compose)

    // Room
    implementation(libs.room)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)

    // Landscapist Glide
    implementation(libs.glide)

    // Benchmark
    implementation(libs.androidx.benchmark.common)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )
    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    // Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
