package com.example.core_api.utils.string_provider

import androidx.annotation.PluralsRes

interface StringProvider {
    fun getString(id: Int): String
    fun getString(id: Int, vararg args: Any): String
    fun getText(id: Int): CharSequence
    fun getStringArray(id: Int): Array<String>
    fun getQuantityString(@PluralsRes id: Int, quantity: Int): String
    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String
}
