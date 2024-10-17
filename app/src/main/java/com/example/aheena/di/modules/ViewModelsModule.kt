package com.example.aheena.di.modules

import androidx.lifecycle.ViewModel
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.core.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(viewModel: MainViewModel): ViewModel
}
