package com.example.aheena.di

import com.example.core.di.DaggerComponent
import com.example.core.di.scope.ApplicationScope
import com.example.core.presentation.base.BaseApplication
import dagger.Component

@ApplicationScope
@Component(
    dependencies = [AppComponentDependencies::class],
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
}
