package com.example.aheena.di.component_builder.factory

import com.example.aheena.di.AppComponent
import com.example.core.di.component.DaggerComponent
import com.example.core.di.component.FactoryProvider

internal class ApplicationFactory(private val appComponent: AppComponent) : FactoryProvider {

    private val factory =
        DaggerComponent.Factory.builder()
            .fromMultipleDependencies { _, _ -> appComponent }
            .build()

    override fun getFactory(dependencyType: String?): DaggerComponent.Factory {
        return factory
    }
}
