package com.example.core_impl.crypto.rsa.model

enum class EncryptionPadding(val id: String) {
    NONE("NoPadding"),
    PKCS5("PKCS5Padding"),
    RSA_OAEP("OAEPPadding"),
    RSA_PKCS1("PKCS1Padding"),
    OAEP_MGF1("OAEPWithSHA-256AndMGF1Padding")
}
