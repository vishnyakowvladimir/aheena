package com.example.core.utils

import android.content.Context
import androidx.annotation.PluralsRes

class StringProviderImpl(private val context: Context) : StringProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

    override fun getText(id: Int): CharSequence {
        return context.getText(id)
    }

    override fun getStringArray(id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    override fun getQuantityString(@PluralsRes id: Int, quantity: Int): String {
        return context.resources.getQuantityString(id, quantity)
    }

    override fun getQuantityString(
        @PluralsRes id: Int,
        quantity: Int,
        vararg formatArgs: Any
    ): String {
        return context.resources.getQuantityString(id, quantity, *formatArgs)
    }
}