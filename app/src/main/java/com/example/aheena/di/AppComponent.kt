package com.example.aheena.di

import com.example.aheena.di.modules.NavigationModule
import com.example.aheena.di.modules.UtilsModule
import com.example.core.di.DaggerComponent
import com.example.core.di.scope.ApplicationScope
import com.example.core.presentation.base.BaseActivity
import com.example.core.presentation.base.BaseApplication
import dagger.Component

@ApplicationScope
@Component(
    dependencies = [AppComponentDependencies::class],
    modules = [
        UtilsModule::class,
        NavigationModule::class,
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
                .create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: AppComponentDependencies,
        ): AppComponent
    }

    fun inject(activity: BaseActivity)
}
