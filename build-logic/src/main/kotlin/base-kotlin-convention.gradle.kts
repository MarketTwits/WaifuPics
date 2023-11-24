import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = localLibs.findVersion("jvm-dot").get().toString()
    }
}
