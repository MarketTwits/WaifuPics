plugins {
    id("kotlin")
}
kotlin {
    jvmToolchain(libs.versions.jvm.get().toString().toInt())
}
