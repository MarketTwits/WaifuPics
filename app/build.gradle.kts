plugins {
    id("android-application-convention")
}

android {

    namespace = libs.versions.namespace.get()

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.asProvider().get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(path = ":modules:features:random_image"))
    implementation(project(path = ":modules:services:core-ui"))
    implementation(project(path = ":modules:features:navigation"))
    implementation(project(path = ":modules:features:gallery"))
}
