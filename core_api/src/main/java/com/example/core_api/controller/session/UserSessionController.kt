package com.example.core_api.controller.session

interface UserSessionController {
    fun enable()
    fun logoutSession()
    fun isEnabled(): Boolean
}
