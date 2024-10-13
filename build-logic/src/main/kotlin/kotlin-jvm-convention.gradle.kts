plugins {
    id("kotlin")
}
kotlin {
  //  jvmToolchain(localLibs.findVersion("jvm").get().toString().toInt())
    jvmToolchain(libs.versions.jvm.get().toString().toInt())
}
