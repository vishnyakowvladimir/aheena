package com.example.core_impl.crypto.vizhener

import com.example.core_api.crypto.vizhener.VizhenerCipher

private const val EMPTY_STRING = ""

class VizhenerCipherImpl(
    private val alphabet: String,
) : VizhenerCipher {

    private val alphabetMap by lazy { alphabet.mapIndexed { index, char -> char to index }.toMap() }

    override fun encrypt(text: CharSequence, key: CharSequence): CharSequence {
        return if (key.isNotEmpty()) {
            text.mapIndexed { index, char ->
                if (alphabetMap.contains(char)) {
                    val textCharIndex = alphabetMap[char] ?: return EMPTY_STRING
                    val keyCharIndex = alphabetMap[key[index % key.length]] ?: return EMPTY_STRING
                    val alphabetIndex = (textCharIndex + keyCharIndex) % alphabet.length
                    alphabet[alphabetIndex]
                } else {
                    char
                }
            }.joinToString(EMPTY_STRING)
        } else {
            EMPTY_STRING
        }
    }

    override fun decrypt(text: CharSequence, key: CharSequence): CharSequence {
        return if (key.isNotEmpty()) {
            text.mapIndexed { index, char ->
                if (alphabetMap.contains(char)) {
                    val textCharIndex = alphabetMap[char] ?: return EMPTY_STRING
                    val keyCharIndex = alphabetMap[key[index % key.length]] ?: return EMPTY_STRING
                    val alphabetIndex = (textCharIndex - keyCharIndex + alphabet.length) % alphabet.length
                    alphabet[alphabetIndex]
                } else {
                    char
                }
            }.joinToString(EMPTY_STRING)
        } else {
            EMPTY_STRING
        }
    }
}
