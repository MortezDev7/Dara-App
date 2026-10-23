package com.x7.dara.model.server.mapper


data class MarketUiModel(
    val gold: GoldMarketUi,
    val currency: CurrencyMarketUi,
    val crypto: CryptoMarketUi
)

data class GoldMarketUi(
    val gold18: MarketItemUi?,
    val gold24: MarketItemUi?,
    val meltedGold: MarketItemUi?,
    val goldOunce: MarketItemUi?,
    val coin1g: MarketItemUi?,
    val quarterCoin: MarketItemUi?,
    val halfCoin: MarketItemUi?,
    val emamiCoin: MarketItemUi?,
    val baharCoin: MarketItemUi?
)

data class CurrencyMarketUi(
    val tether: MarketItemUi?,
    val dollar: MarketItemUi?,
    val euro: MarketItemUi?,
    val dirham: MarketItemUi?,
    val pound: MarketItemUi?,
    val yen: MarketItemUi?,
    val dinarKuwait: MarketItemUi?,
    val dollarAustralia: MarketItemUi?,
    val dollarCanada: MarketItemUi?,
    val yuan: MarketItemUi?,
    val liraTurkey: MarketItemUi?,
    val riyalSaudi: MarketItemUi?,
    val francSwiss: MarketItemUi?,
    val rupeeIndia: MarketItemUi?,
    val rupeePakistan: MarketItemUi?,
    val dinarIraq: MarketItemUi?,
    val liraSyria: MarketItemUi?,
    val kronaSweden: MarketItemUi?,
    val riyalQatar: MarketItemUi?,
    val rialOman: MarketItemUi?,
    val dinarBahrain: MarketItemUi?,
    val afghani: MarketItemUi?,
    val ringgitMalaysia: MarketItemUi?,
    val bahtThailand: MarketItemUi?,
    val rubleRussia: MarketItemUi?,
    val manatAzerbaijan: MarketItemUi?,
    val dramArmenia: MarketItemUi?,
    val lariGeorgia: MarketItemUi?
)

data class CryptoMarketUi(
    val bitcoin: MarketItemUi?,
    val ethereum: MarketItemUi?,
    val tether: MarketItemUi?,
    val xrp: MarketItemUi?,
    val bnb: MarketItemUi?,
    val solana: MarketItemUi?,
    val usdc: MarketItemUi?,
    val tron: MarketItemUi?,
    val dogecoin: MarketItemUi?,
    val cardano: MarketItemUi?,
    val chainlink: MarketItemUi?,
    val stellar: MarketItemUi?,
    val avalanche: MarketItemUi?,
    val shibaInu: MarketItemUi?,
    val litecoin: MarketItemUi?,
    val polkadot: MarketItemUi?,
    val uniswap: MarketItemUi?,
    val cosmos: MarketItemUi?,
    val filecoin: MarketItemUi?
)