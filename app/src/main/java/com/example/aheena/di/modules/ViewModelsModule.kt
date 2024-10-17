package com.example.aheena.di.modules

import androidx.lifecycle.ViewModel
import com.example.aheena.presentation.MainViewModel
import com.example.core.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(viewModel: MainViewModel): ViewModel
}
