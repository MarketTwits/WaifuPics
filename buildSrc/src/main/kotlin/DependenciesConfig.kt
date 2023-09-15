interface DependenciesConfig {
    fun fetch(): String
    abstract class BaseAbsract : DependenciesConfig {
        protected abstract val version: String
        protected abstract val dependence: String
        override fun fetch() = dependence + version
    }
    object Compose {
        private const val composeVersion = "1.4.3"
        private const val composeBomVersion = "2023.03.00"
        private const val composeActivityVersion = "1.7.2"
        private const val composeLifecycleVersion = "2.6.1"
        const val uiUi = "androidx.compose.ui:ui"
        const val graphics = "androidx.compose.ui:ui-graphics"
        const val toolingPreiew = "androidx.compose.ui:ui-tooling-preview"
        const val material = "androidx.compose.material3:material3"
        const val composeBom = "androidx.compose:compose-bom:$composeBomVersion"
        const val activity ="androidx.activity:activity-compose:$composeActivityVersion"
        const val composeLifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:$composeLifecycleVersion"
        const val icons = "androidx.compose.material:material-icons-extended:$composeVersion"

        object Navigation{
            private val navigationVersion = "2.7.1"
             val navigation =  "androidx.navigation:navigation-compose:$navigationVersion"
        }
    }
    object ComposeDebugAndTest {
        val uiTooling = "androidx.compose.ui:ui-tooling"
        val manifest = "androidx.compose.ui:ui-test-manifest"
        val bom = "androidx.compose:compose-bom:2023.03.00"
        val junit = "androidx.compose.ui:ui-test-junit4"
    }
    object Espresso : BaseAbsract(){
        override val version = "3.5.1"
        override val dependence = "androidx.test.espresso:espresso-core:"
    }
    object Retrofit{
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofitConverter = "com.squareup.retrofit2:converter-gson:$version"
    }


    object Dagger2 : BaseAbsract() {
        override val version = "2.46"
        override val dependence = "com.google.dagger:dagger-android:"
    }

    object Lifecycle : BaseAbsract() {
        override val version = "2.6.1"
        override val dependence = "androidx.lifecycle:lifecycle-runtime-ktx:"
    }

    object JuintExt : BaseAbsract() {
        override val version = "1.1.5"
        override val dependence = "androidx.test.ext:junit:"
    }
    object KotlinCore : BaseAbsract(){
        override val version = "1.9.0"
        override val dependence = "androidx.core:core-ktx:"
    }

    object Junit : BaseAbsract() {
        override val version = "4.13.2"
        override val dependence = "junit:junit:"
    }
    object CoilCompose : BaseAbsract(){
        override val version = "2.4.0"
        override val dependence = "io.coil-kt:coil-compose:"
    }
    object WorkManager : BaseAbsract(){
        override val version = "2.8.1"
        override val dependence = "androidx.work:work-runtime-ktx:"
    }
    object Zoomable : BaseAbsract(){
        override val version = "1.5.1"
        override val dependence = "net.engawapg.lib:zoomable:"
    }
}
