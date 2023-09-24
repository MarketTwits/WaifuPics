import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.Dependency

fun DependencyHandler.implementation(dependancy : String){
    add("implementation", dependancy)
}
fun DependencyHandler.api(dependancy : String){
    add("api", dependancy)
}
fun DependencyHandler.test(dependancy : String){
    add("test", dependancy)
}
fun DependencyHandler.testApi(dependancy : String){
    add("testApi", dependancy)
}
fun DependencyHandler.androidTestApi(dependancy : String){
    add("androidTestApi", dependancy)
}
fun DependencyHandler.androidTestApi(dependancy : Dependency){
    add("androidTestApi", dependancy)
}
fun DependencyHandler.androidTest(dependancy : String){
    add("androidTest", dependancy)
}
fun DependencyHandler.debug(dependancy : String){
    add("debug", dependancy)
}
fun DependencyHandler.debugApi(dependancy : String){
    add("debugApi", dependancy)
}
fun DependencyHandler.debugImplementation(dependancy : String){
    add("debugImplementation", dependancy)
}
fun DependencyHandler.api(dependancy : Dependency){
    add("api", dependancy)
}

