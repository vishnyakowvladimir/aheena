package com.example.aheena.di.component_builder.factory

import com.example.aheena.di.AppComponent
import com.example.core.di.component.DaggerComponent
import com.example.core.di.component.FactoryProvider
import com.example.feature_authentication.di.AuthenticationComponent

internal class AuthenticationFactory() : FactoryProvider {

//    private val factory = DaggerComponent.Factory.builder()
//        .fromMultipleDependencies { factories, components ->
//            getUserLifecycleComponents(factories, components).run {
//                AuthenticationComponent.build(
//                    appComponent.authenticationDependencies(),
//                )
//            }
//        }
//        .build()

    private val factory: DaggerComponent.Factory by lazy {
        DaggerComponent.Factory.builder()
            .fromSingleDependency<AppComponent> { component ->
                AuthenticationComponent.build(component.authenticationDependencies())
            }
            .build()
    }

    override fun getFactory(dependencyType: String?): DaggerComponent.Factory {
        return factory
    }
}
