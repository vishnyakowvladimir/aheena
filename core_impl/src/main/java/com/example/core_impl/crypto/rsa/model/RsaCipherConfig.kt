package com.example.core_impl.crypto.rsa.model

import java.security.spec.MGF1ParameterSpec

data class RsaCipherConfig(
    val cipherMode: CipherMode,
    val encryptionPadding: EncryptionPadding,
    val mdName: String,
    val mgfName: String,
    val mgfSpec: MGF1ParameterSpec,
)
