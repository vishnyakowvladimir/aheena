package com.example.feature_itunes.di

import android.content.Context
import com.example.core.di.qualifier.MainRouter
import com.example.core.holder.ActivityHolder
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.string_provider.StringProvider
import com.example.data_sdk_api.interactor.itunes.ItunesInteractor
import javax.inject.Inject

class ItunesExternalDependencies @Inject constructor(
    val applicationContext: Context,
    val activityHolder: ActivityHolder,
    @get:MainRouter @MainRouter val mainRouter: NavRouter,
    val stringProvider: StringProvider,
    val itunesInteractor: ItunesInteractor,
)
