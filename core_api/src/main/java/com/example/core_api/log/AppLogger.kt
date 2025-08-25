package com.example.core_api.log

import android.util.Log

private const val LOG_TAG = "aheena_log"

object AppLogger {

    fun log(text: String) {
        Log.d(LOG_TAG, text)
    }
}
