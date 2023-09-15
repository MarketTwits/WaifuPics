plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.markettwits.waifupics"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.markettwits.waifupics"
        minSdk = 24
        targetSdk = 34
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
    implementation(DependenciesConfig.Retrofit.retrofit)
    implementation(DependenciesConfig.Retrofit.retrofitConverter)
    implementation(DependenciesConfig.Zoomable.fetch())
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation(DependenciesConfig.Compose.icons)
    implementation(DependenciesConfig.WorkManager.fetch())
    implementation(DependenciesConfig.Dagger2.fetch())
    implementation(DependenciesConfig.KotlinCore.fetch())
    implementation(DependenciesConfig.Lifecycle.fetch())
    implementation(DependenciesConfig.Compose.activity)
    implementation(platform(DependenciesConfig.Compose.composeBom))
    implementation(DependenciesConfig.Compose.uiUi)
    implementation(DependenciesConfig.Compose.graphics)
    implementation(DependenciesConfig.Compose.toolingPreiew)
    implementation(DependenciesConfig.Compose.material)
    implementation(DependenciesConfig.Compose.Navigation.navigation)
    implementation(DependenciesConfig.Compose.composeLifecycle)
    implementation(DependenciesConfig.CoilCompose.fetch())
    //implementation("androidx.compose.material:material:1.5.1")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.2")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    //implementation("androidx.compose.material3:material3-android:1.2.0-alpha07")
    testImplementation(DependenciesConfig.Junit.fetch())
    androidTestImplementation(DependenciesConfig.JuintExt.fetch())
    androidTestImplementation(DependenciesConfig.Espresso.fetch())
    androidTestImplementation(platform(DependenciesConfig.ComposeDebugAndTest.bom))
    androidTestImplementation(DependenciesConfig.ComposeDebugAndTest.junit)
    debugImplementation(DependenciesConfig.ComposeDebugAndTest.uiTooling)
    debugImplementation(DependenciesConfig.ComposeDebugAndTest.manifest)
}