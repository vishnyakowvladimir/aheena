package com.example.aheena.di.component_builder.factory

import com.example.aheena.di.AppComponent
import com.example.core.di.component.DaggerComponent
import com.example.core.di.component.FactoryProvider
import com.example.feature_tech.di.TechComponent

internal class TechFactory() : FactoryProvider {

    private val factory: DaggerComponent.Factory by lazy {
        DaggerComponent.Factory.builder()
            .fromSingleDependency<AppComponent> { component ->
                TechComponent.build(component.techDependencies())
            }
            .build()
    }

    override fun getFactory(dependencyType: String?): DaggerComponent.Factory {
        return factory
    }
}
