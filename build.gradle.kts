// Top-level build file where you can add configuration options common to all sub-projects/modules.

tasks.withType<Wrapper>().configureEach {
    distributionType = Wrapper.DistributionType.BIN
    gradleVersion = libs.versions.gradle.get()
}
