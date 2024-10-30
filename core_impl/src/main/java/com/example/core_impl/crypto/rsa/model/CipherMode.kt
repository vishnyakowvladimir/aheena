package com.example.core_impl.crypto.rsa.model

enum class CipherMode(val id: String) {
    CBC("CBC"),
    CTR("CTR"),
    ECB("ECB"),
    GCM("GCM")
}
