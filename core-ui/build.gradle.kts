plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.markettwits.core_ui"
    compileSdk = Config.SDK.compileSdk

    defaultConfig {
        minSdk = Config.SDK.minSdk

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion  = DependenciesConfig.Compose.composeVersion
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
    api(project(path = ":core"))

    api(DependenciesConfig.Compose.icons)
    api(DependenciesConfig.Lifecycle.fetch())
    api(DependenciesConfig.Compose.activity)
    api(platform(DependenciesConfig.Compose.composeBom))
    //api(DependenciesConfig.Compose.uiUi)
    api(DependenciesConfig.Compose.runtime)
    api(DependenciesConfig.Compose.graphics)
    api(DependenciesConfig.ComposeDebugAndTest.toolingPreiew)
    api(DependenciesConfig.Compose.material3)
    api(DependenciesConfig.Compose.Navigation.navigation)
    api(DependenciesConfig.Compose.composeLifecycle)
    api(DependenciesConfig.CoilCompose.fetch())
    api(DependenciesConfig.Compose.material)
    api (DependenciesConfig.Compose.acomponist)
    androidTestApi(DependenciesConfig.JuintExt.fetch())
    androidTestApi(DependenciesConfig.Espresso.fetch())
    androidTestApi(platform(DependenciesConfig.ComposeDebugAndTest.bom))
    androidTestApi(DependenciesConfig.ComposeDebugAndTest.junit)
    debugApi(DependenciesConfig.ComposeDebugAndTest.uiTooling)
    debugApi(DependenciesConfig.ComposeDebugAndTest.manifest)


    api("androidx.test.ext:junit-ktx:1.1.5")
    testApi("org.testng:testng:6.9.6")
}