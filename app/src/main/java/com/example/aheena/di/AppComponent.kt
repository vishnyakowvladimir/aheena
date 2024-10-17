package com.example.aheena.di

import android.content.Context
import com.example.aheena.di.modules.NavigationModule
import com.example.aheena.di.modules.UtilsModule
import com.example.aheena.di.modules.ViewModelsModule
import com.example.aheena.presentation.MainActivity
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
        ViewModelsModule::class,
    ],
)
internal interface AppComponent : DaggerComponent {

    companion object {

        fun init(
            app: BaseApplication,
        ): AppComponent {
            val dependencies = AppComponentDependencies(app)
            return DaggerAppComponent
                .factory()
                .create(
                    dependencies = dependencies,
                    context = app,
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: AppComponentDependencies,
            @BindsInstance context: Context,
        ): AppComponent
    }

    fun inject(activity: MainActivity)
}
