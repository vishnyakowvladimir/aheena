package com.example.feature_motion_layout.di

import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.router.NavRouter
import javax.inject.Inject

class MotionLayoutExternalDependencies @Inject constructor(
    @get:MainRouter @MainRouter val mainRouter: NavRouter,
)
