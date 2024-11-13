package com.example.aheena.di

import android.content.Context
import com.example.aheena.di.dependencies.FeaturesDependenciesProvider
import com.example.aheena.di.modules.ApiModule
import com.example.aheena.di.modules.CacheModule
import com.example.aheena.di.modules.ControllerModule
import com.example.aheena.di.modules.CryptoModule
import com.example.aheena.di.modules.FeatureComposablesModule
import com.example.aheena.di.modules.InteractorModule
import com.example.aheena.di.modules.NavigationModule
import com.example.aheena.di.modules.NetworkModule
import com.example.aheena.di.modules.PresentationModule
import com.example.aheena.di.modules.RepositoryModule
import com.example.aheena.di.modules.StorageModule
import com.example.aheena.di.modules.UtilsModule
import com.example.aheena.di.modules.ViewModelsModule
import com.example.aheena.presentation.MainActivity
import com.example.core.application_info.ApplicationInfo
import com.example.core.di.component.DaggerComponent
import com.example.core.di.scope.ApplicationScope
import com.example.core.presentation.base.BaseApplication
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    dependencies = [AppComponentDependencies::class],
    modules = [
        UtilsModule::class,
        NavigationModule::class,
        FeatureComposablesModule::class,
        ViewModelsModule::class,
        NetworkModule::class,
        CacheModule::class,
        ApiModule::class,
        StorageModule::class,
        RepositoryModule::class,
        InteractorModule::class,
        PresentationModule::class,
        CryptoModule::class,
        ControllerModule::class,
    ],
)
internal interface AppComponent : DaggerComponent, FeaturesDependenciesProvider {

    companion object {

        fun init(
            app: BaseApplication,
            applicationInfo: ApplicationInfo,
        ): AppComponent {
            val dependencies = AppComponentDependencies(app)
            return DaggerAppComponent
                .factory()
                .create(
                    dependencies = dependencies,
                    applicationInfo = applicationInfo,
                    applicationContext = app,
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: AppComponentDependencies,
            @BindsInstance applicationInfo: ApplicationInfo,
            @BindsInstance applicationContext: Context,
        ): AppComponent
    }

    fun inject(activity: MainActivity)
}
