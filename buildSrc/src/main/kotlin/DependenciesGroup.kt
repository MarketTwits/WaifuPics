import org.gradle.api.artifacts.dsl.DependencyHandler

object DependenciesGroup {

    object Compose{
        fun DependencyHandler.composeUiApi(){
            api(DependenciesConfig.Compose.icons)
            api(DependenciesConfig.Lifecycle.fetch())
            api(DependenciesConfig.Compose.activity)
            api(platform(DependenciesConfig.Compose.composeBom))
            api(DependenciesConfig.Compose.runtime)
            api(DependenciesConfig.Compose.graphics)
            api(DependenciesConfig.ComposeDebugAndTest.toolingPreiew)
            api(DependenciesConfig.Compose.material3)
            api(DependenciesConfig.Compose.Navigation.navigation)
            api(DependenciesConfig.Compose.composeLifecycle)
            api(DependenciesConfig.CoilCompose.fetch())
            api(DependenciesConfig.Compose.material)
            api (DependenciesConfig.Compose.acomponist)
            androidTestApi(DependenciesConfig.JuintExtKtx.fetch())
            androidTestApi(DependenciesConfig.Espresso.fetch())
            androidTestApi(platform(DependenciesConfig.ComposeDebugAndTest.bom))
            androidTestApi(DependenciesConfig.ComposeDebugAndTest.junit)
            debugApi(DependenciesConfig.ComposeDebugAndTest.uiTooling)
            debugApi(DependenciesConfig.ComposeDebugAndTest.manifest)
            api(DependenciesConfig.JuintExtKtx.fetch())
            testApi(DependenciesConfig.Testing.fetch())
        }
    }
    object Retrofit{
        fun DependencyHandler.retrofitImpl(){
            implementation(DependenciesConfig.Retrofit.retrofit)
            implementation(DependenciesConfig.Retrofit.retrofitConverter)
        }
        fun DependencyHandler.okkhttpImpl(){
            implementation(DependenciesConfig.Okkhttp.okhttp3)
            implementation(DependenciesConfig.Okkhttp.okhttp3LogginInterceptor)
        }
    }



}
