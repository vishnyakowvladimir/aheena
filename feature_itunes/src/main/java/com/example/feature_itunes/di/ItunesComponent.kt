package com.example.feature_itunes.di

import com.example.core.di.component.DaggerComponent
import com.example.core.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [ItunesExternalDependencies::class],
    modules = [
        ItunesModule::class,
    ],
)
interface ItunesComponent : DaggerComponent, ItunesDependenciesProvider {

    companion object {

        fun build(
            dependencies: ItunesExternalDependencies,
        ): ItunesComponent {
            return DaggerItunesComponent
                .factory()
                .create(
                    dependencies = dependencies,
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: ItunesExternalDependencies,
        ): ItunesComponent
    }
}
