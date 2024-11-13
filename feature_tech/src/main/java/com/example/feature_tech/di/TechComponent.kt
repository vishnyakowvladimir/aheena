package com.example.feature_tech.di

import com.example.core.di.component.DaggerComponent
import com.example.core.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [TechExternalDependencies::class],
    modules = [
        TechModule::class,
    ],
)
interface TechComponent : DaggerComponent, TechDependenciesProvider {

    companion object {

        fun build(
            dependencies: TechExternalDependencies,
        ): TechComponent {
            return DaggerTechComponent
                .factory()
                .create(
                    dependencies = dependencies,
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: TechExternalDependencies,
        ): TechComponent
    }
}
