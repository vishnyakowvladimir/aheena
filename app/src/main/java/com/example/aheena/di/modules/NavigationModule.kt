package com.example.aheena.di.modules

import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.core.di.scope.ApplicationScope
import com.example.core_impl.navigation.NavControllerHolder
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavHostControllerHolder(): NavControllerHolder {
        return NavControllerHolder()
    }

    @ApplicationScope
    @Provides
    fun provideComposablesHolder(): FeatureComposablesHolder {
        return FeatureComposablesHolder()
    }
}
