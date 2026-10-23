package com.x7.dara.model.server

data class BrsResponse(
    val gold: List<BrsAsset>,
    val currency: List<BrsAsset>,
    val cryptocurrency: List<BrsCrypto>
)



