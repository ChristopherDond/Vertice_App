package com.vertice.app.core.session

data class Session(
    val token: String,
    val userId: String,
    val displayName: String,
    val isPro: Boolean,
    val violetaVerified: Boolean,
)
