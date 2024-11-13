package com.example.aheena.di.modules

import com.example.core_api.di.scope.ApplicationScope
import com.example.core_api.holder.ActivityHolder
import com.example.core_impl.holder.ActivityHolderImpl
import dagger.Module
import dagger.Provides

@Module
interface PresentationModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideActivityHolder(): ActivityHolder {
            return ActivityHolderImpl()
        }
    }
}
