package com.example.feature_authentication.extension

fun List<Int>.convertToCharSequence(): CharSequence {
    return fold("") { currentString, value ->
        currentString.plus(value)
    }
}
