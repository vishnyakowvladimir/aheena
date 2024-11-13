package com.example.core_api.navigation.router

import com.example.core_api.navigation.base.BaseDestination

interface NavRouter {
    fun navigate(destination: BaseDestination): Boolean
    fun replace(destination: BaseDestination): Boolean
    fun replaceAll(destination: BaseDestination): Boolean
    fun popBackStack(): Boolean
}
