package com.example.feature_motion_layout.di

import com.example.core_api.di.component.DaggerComponent
import com.example.core_api.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [MotionLayoutExternalDependencies::class],
)
interface MotionLayoutComponent : DaggerComponent, MotionLayoutDependenciesProvider {

    companion object {

        fun build(
            dependencies: MotionLayoutExternalDependencies,
        ): MotionLayoutComponent {
            return DaggerMotionLayoutComponent
                .factory()
                .create(
                    dependencies = dependencies,
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: MotionLayoutExternalDependencies,
        ): MotionLayoutComponent
    }
}
