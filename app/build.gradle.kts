plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.markettwits.waifupics"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.markettwits.waifupics"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(DependenciesConfig.Dagger2.fetch())
    implementation(DependenciesConfig.KotlinCore.fetch())
    implementation(DependenciesConfig.Lifecycle.fetch())
    implementation(DependenciesConfig.Compose.activity)
    implementation(platform(DependenciesConfig.Compose.composeBom))
    implementation(DependenciesConfig.Compose.uiUi)
    implementation(DependenciesConfig.Compose.graphics)
    implementation(DependenciesConfig.Compose.toolingPreiew)
    implementation(DependenciesConfig.Compose.material)
    implementation(DependenciesConfig.CoilCompose.fetch())
    testImplementation(DependenciesConfig.Junit.fetch())
    androidTestImplementation(DependenciesConfig.JuintExt.fetch())
    androidTestImplementation(DependenciesConfig.Espresso.fetch())
    androidTestImplementation(platform(DependenciesConfig.ComposeDebugAndTest.bom))
    androidTestImplementation(DependenciesConfig.ComposeDebugAndTest.junit)
    debugImplementation(DependenciesConfig.ComposeDebugAndTest.uiTooling)
    debugImplementation(DependenciesConfig.ComposeDebugAndTest.manifest)
}