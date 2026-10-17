package com.x7.dara.model.brs

data class BrsResponse(
    val gold: List<BrsAsset>,
    val currency: List<BrsAsset>,
    val cryptocurrency: List<BrsCrypto>
)



