package com.example.feature_authentication.di

import com.example.core_api.di.component.DaggerComponent
import com.example.core_api.di.scope.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    dependencies = [AuthenticationExternalDependencies::class],
    modules = [
        AuthenticationModule::class,
    ],
)
interface AuthenticationComponent : DaggerComponent, AuthenticationDependenciesProvider {

    companion object {

        fun build(
            dependencies: AuthenticationExternalDependencies,
        ): AuthenticationComponent {
            return DaggerAuthenticationComponent
                .factory()
                .create(
                    dependencies = dependencies,
                )
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dependencies: AuthenticationExternalDependencies,
        ): AuthenticationComponent
    }
}
