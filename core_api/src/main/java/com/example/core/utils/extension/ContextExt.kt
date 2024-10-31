package com.example.core.utils.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource

fun Context.findActivity(): Activity? {
    return findActivity<Activity>()
}

@JvmName("findSpecificActivity")
inline fun <reified T : Activity> Context.findActivity(): T? {
    var context = this
    while (context is ContextWrapper) {
        if (context is T) {
            return context
        }
        context = context.baseContext
    }
    return null
}

@Composable
fun getAttributeColor(@AttrRes colorResId: Int): Color {
    return colorResource(
        id = LocalContext.current.getColorFromAttribute(colorResId).resourceId,
    )
}

fun Context.getColorFromAttribute(attr: Int): TypedValue {
    return TypedValue().apply {
        theme.resolveAttribute(attr, this, true)
    }
}
