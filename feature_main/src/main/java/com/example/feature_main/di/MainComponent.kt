package com.example.feature_main.di

import com.example.core.di.component.DaggerComponent
import com.example.core.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [MainExternalDependencies::class],
    modules = [
        MainModule::class,
    ],
)
interface MainComponent : DaggerComponent, MainDependenciesProvider {

    companion object {

        fun build(
            dependencies: MainExternalDependencies,
        ): MainComponent {
            return DaggerMainComponent
                .factory()
                .create(
                    dependencies = dependencies,
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: MainExternalDependencies,
        ): MainComponent
    }
}
