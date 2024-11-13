package com.example.feature_tech.di

import com.example.core_api.di.component.DaggerComponent
import com.example.core_api.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [TechExternalDependencies::class],
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
