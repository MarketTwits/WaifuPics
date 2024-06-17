plugins {
    id("kotlin")
}
kotlin {
    jvmToolchain(localLibs.findVersion("jvm").get().toString().toInt())
}
