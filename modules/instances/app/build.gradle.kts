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
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.compose.activity)
    implementation(libs.core.splashscreen)
    implementation(project(path = ":modules:features:core-ui"))
    implementation(project(":modules:features:async"))
    implementation(project(path = ":modules:features:root"))
    implementation(project(path = ":modules:features:cache-datasource"))
}
