plugins {
    id("android-application-convention")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {

    namespace = libs.versions.namespace.get()

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.core.splashscreen)
    implementation(project(path = ":modules:services:core-ui"))
    implementation(project(path = ":modules:services:core"))
    implementation(project(path = ":modules:features:root"))
    implementation(project(":modules:services:cache-datasource"))
}
