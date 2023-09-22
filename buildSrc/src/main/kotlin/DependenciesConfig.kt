interface DependenciesConfig {
    fun fetch(): String

    object Compose {
        const val composeVersion = "1.5.3"
        private const val composeUiVersion = "1.5.1"
        private const val composeBomVersion = "2023.03.00"
        private const val composeActivityVersion = "1.7.2"
        private const val composeLifecycleVersion = "2.6.1"
        private const val composeMaterial3 = "1.1.1"
        private const val acomponistVersion = "0.27.0"

        const val uiUi = "androidx.compose.ui:ui:$composeUiVersion"
        const val graphics = "androidx.compose.ui:ui-graphics:$composeUiVersion"
        const val material3 = "androidx.compose.material3:material3:$composeMaterial3"
        const val material = "androidx.compose.material:material:$composeUiVersion"
        const val composeBom = "androidx.compose:compose-bom:$composeBomVersion"
        const val activity = "androidx.activity:activity-compose:$composeActivityVersion"
        const val composeLifecycle =
            "androidx.lifecycle:lifecycle-viewmodel-compose:$composeLifecycleVersion"
        const val icons = "androidx.compose.material:material-icons-extended:$composeUiVersion"
        const val runtime = "androidx.compose.runtime:runtime:$composeUiVersion"
        const val acomponist =
            "com.google.accompanist:accompanist-systemuicontroller:$acomponistVersion"

        object Navigation {
            private val navigationVersion = "2.7.1"
            val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
        }
    }

    object ComposeDebugAndTest {
        private const val uiToolingVersion = "1.4.3"
        private const val junitComposeVersion = "1.5.1"
        private const val composeBomVersion = "2023.03.00"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$uiToolingVersion"
        const val toolingPreiew = "androidx.compose.ui:ui-tooling-preview:$uiToolingVersion"
        const val manifest = "androidx.compose.ui:ui-test-manifest"
        const val bom = "androidx.compose:compose-bom:2023.03.00"
        const val junit = "androidx.compose.ui:ui-test-junit4:$junitComposeVersion"
    }

    object Espresso : DependaciesAbstract() {
        override val version = "3.5.1"
        override val dependence = "androidx.test.espresso:espresso-core:"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val retrofitConverter = "com.squareup.retrofit2:converter-gson:$version"
    }


    object Dagger2 : DependaciesAbstract() {
        override val version = "2.46"
        override val dependence = "com.google.dagger:dagger-android:"
    }

    object Lifecycle : DependaciesAbstract() {
        override val version = "2.6.1"
        override val dependence = "androidx.lifecycle:lifecycle-runtime-ktx:"
    }

    //    object JuintExt : DependaciesAbstract() {
//        override val version = "1.1.5"
//        override val dependence = "androidx.test.ext:junit:"
//    }
    object JuintExt : DependaciesAbstract() {
        override val version = "1.1.5"
        override val dependence = "androidx.test.ext:junit-ktx:"
    }

    object Testing : DependaciesAbstract(){
        override val version = "6.9.6"
        override val dependence = "org.testng:testng:"
    }

    object KotlinCore : DependaciesAbstract() {
        override val version = "1.9.1"
        override val dependence = "androidx.core:core-ktx:"
    }

    object Junit : DependaciesAbstract() {
        override val version = "4.13.2"
        override val dependence = "junit:junit:"
    }

    object CoilCompose : DependaciesAbstract() {
        override val version = "2.4.0"
        override val dependence = "io.coil-kt:coil-compose:"
    }

    object WorkManager : DependaciesAbstract() {
        override val version = "2.8.1"
        override val dependence = "androidx.work:work-runtime-ktx:"
    }

    object Zoomable : DependaciesAbstract() {
        override val version = "1.5.1"
        override val dependence = "net.engawapg.lib:zoomable:"
    }
}
