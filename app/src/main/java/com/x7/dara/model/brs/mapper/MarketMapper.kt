package com.x7.dara.model.brs.mapper

import com.x7.dara.model.brs.BrsAsset
import com.x7.dara.model.brs.BrsCrypto
import com.x7.dara.model.brs.BrsResponse


object MarketMapper {

    fun map(response: BrsResponse): MarketUiModel {

        return MarketUiModel(

            gold = GoldMarketUi(
                gold18 = response.gold
                    .find { it.symbol == "IR_GOLD_18K" }
                    ?.toGoldUi(),

                gold24 = response.gold
                    .find { it.symbol == "IR_GOLD_24K" }
                    ?.toGoldUi(),

                meltedGold = response.gold
                    .find { it.symbol == "IR_GOLD_MELTED" }
                    ?.toGoldUi(),

                goldOunce = response.gold
                    .find { it.symbol == "XAUUSD" }
                    ?.toGoldUi(),

                coin1g = response.gold
                    .find { it.symbol == "IR_COIN_1G" }
                    ?.toGoldUi(),

                quarterCoin = response.gold
                    .find { it.symbol == "IR_COIN_QUARTER" }
                    ?.toGoldUi(),

                halfCoin = response.gold
                    .find { it.symbol == "IR_COIN_HALF" }
                    ?.toGoldUi(),

                emamiCoin = response.gold
                    .find { it.symbol == "IR_COIN_EMAMI" }
                    ?.toGoldUi(),

                baharCoin = response.gold
                    .find { it.symbol == "IR_COIN_BAHAR" }
                    ?.toGoldUi()
            ),

            currency = CurrencyMarketUi(
                tether = response.currency
                    .find { it.symbol == "USDT_IRT" }
                    ?.toCurrencyUi(),

                dollar = response.currency
                    .find { it.symbol == "USD" }
                    ?.toCurrencyUi(),

                euro = response.currency
                    .find { it.symbol == "EUR" }
                    ?.toCurrencyUi(),

                dirham = response.currency
                    .find { it.symbol == "AED" }
                    ?.toCurrencyUi(),

                pound = response.currency
                    .find { it.symbol == "GBP" }
                    ?.toCurrencyUi(),

                yen = response.currency
                    .find { it.symbol == "JPY" }
                    ?.toCurrencyUi(),

                dinarKuwait = response.currency
                    .find { it.symbol == "KWD" }
                    ?.toCurrencyUi(),

                dollarAustralia = response.currency
                    .find { it.symbol == "AUD" }
                    ?.toCurrencyUi(),

                dollarCanada = response.currency
                    .find { it.symbol == "CAD" }
                    ?.toCurrencyUi(),

                yuan = response.currency
                    .find { it.symbol == "CNY" }
                    ?.toCurrencyUi(),

                liraTurkey = response.currency
                    .find { it.symbol == "TRY" }
                    ?.toCurrencyUi(),

                riyalSaudi = response.currency
                    .find { it.symbol == "SAR" }
                    ?.toCurrencyUi(),

                francSwiss = response.currency
                    .find { it.symbol == "CHF" }
                    ?.toCurrencyUi(),

                rupeeIndia = response.currency
                    .find { it.symbol == "INR" }
                    ?.toCurrencyUi(),

                rupeePakistan = response.currency
                    .find { it.symbol == "PKR" }
                    ?.toCurrencyUi(),

                dinarIraq = response.currency
                    .find { it.symbol == "IQD" }
                    ?.toCurrencyUi(),

                liraSyria = response.currency
                    .find { it.symbol == "SYP" }
                    ?.toCurrencyUi(),

                kronaSweden = response.currency
                    .find { it.symbol == "SEK" }
                    ?.toCurrencyUi(),

                riyalQatar = response.currency
                    .find { it.symbol == "QAR" }
                    ?.toCurrencyUi(),

                rialOman = response.currency
                    .find { it.symbol == "OMR" }
                    ?.toCurrencyUi(),

                dinarBahrain = response.currency
                    .find { it.symbol == "BHD" }
                    ?.toCurrencyUi(),

                afghani = response.currency
                    .find { it.symbol == "AFN" }
                    ?.toCurrencyUi(),

                ringgitMalaysia = response.currency
                    .find { it.symbol == "MYR" }
                    ?.toCurrencyUi(),

                bahtThailand = response.currency
                    .find { it.symbol == "THB" }
                    ?.toCurrencyUi(),

                rubleRussia = response.currency
                    .find { it.symbol == "RUB" }
                    ?.toCurrencyUi(),

                manatAzerbaijan = response.currency
                    .find { it.symbol == "AZN" }
                    ?.toCurrencyUi(),

                dramArmenia = response.currency
                    .find { it.symbol == "AMD" }
                    ?.toCurrencyUi(),

                lariGeorgia = response.currency
                    .find { it.symbol == "GEL" }
                    ?.toCurrencyUi()
            ),

            crypto = CryptoMarketUi(
                bitcoin = response.cryptocurrency
                    .find { it.symbol == "BTC" }
                    ?.toCryptoUi(),

                ethereum = response.cryptocurrency
                    .find { it.symbol == "ETH" }
                    ?.toCryptoUi(),

                tether = response.cryptocurrency
                    .find { it.symbol == "USDT" }
                    ?.toCryptoUi(),

                xrp = response.cryptocurrency
                    .find { it.symbol == "XRP" }
                    ?.toCryptoUi(),

                bnb = response.cryptocurrency
                    .find { it.symbol == "BNB" }
                    ?.toCryptoUi(),

                solana = response.cryptocurrency
                    .find { it.symbol == "SOL" }
                    ?.toCryptoUi(),

                usdc = response.cryptocurrency
                    .find { it.symbol == "USDC" }
                    ?.toCryptoUi(),

                tron = response.cryptocurrency
                    .find { it.symbol == "TRX" }
                    ?.toCryptoUi(),

                dogecoin = response.cryptocurrency
                    .find { it.symbol == "DOGE" }
                    ?.toCryptoUi(),

                cardano = response.cryptocurrency
                    .find { it.symbol == "ADA" }
                    ?.toCryptoUi(),

                chainlink = response.cryptocurrency
                    .find { it.symbol == "LINK" }
                    ?.toCryptoUi(),

                stellar = response.cryptocurrency
                    .find { it.symbol == "XLM" }
                    ?.toCryptoUi(),

                avalanche = response.cryptocurrency
                    .find { it.symbol == "AVAX" }
                    ?.toCryptoUi(),

                shibaInu = response.cryptocurrency
                    .find { it.symbol == "SHIB" }
                    ?.toCryptoUi(),

                litecoin = response.cryptocurrency
                    .find { it.symbol == "LTC" }
                    ?.toCryptoUi(),

                polkadot = response.cryptocurrency
                    .find { it.symbol == "DOT" }
                    ?.toCryptoUi(),

                uniswap = response.cryptocurrency
                    .find { it.symbol == "UNI" }
                    ?.toCryptoUi(),

                cosmos = response.cryptocurrency
                    .find { it.symbol == "ATOM" }
                    ?.toCryptoUi(),

                filecoin = response.cryptocurrency
                    .find { it.symbol == "FIL" }
                    ?.toCryptoUi()
            )
        )
    }

    private fun BrsAsset.toGoldUi(): MarketItemUi {
        return MarketItemUi(
            symbol = symbol,
            title = name,
            price = formatNumber(price),
            unit = unit,
            changeValue = formatNumber(change_value),
            changePercent = formatPercent(change_percent),
            isPositive = trend(change_percent),
            category = MarketCategory.GOLD
        )
    }

    private fun BrsAsset.toCurrencyUi(): MarketItemUi {
        return MarketItemUi(
            symbol = symbol,
            title = name,
            price = formatNumber(price),
            unit = unit,
            changeValue = formatNumber(change_value),
            changePercent = formatPercent(change_percent),
            isPositive = trend(change_percent),
            category = MarketCategory.CURRENCY
        )
    }

    private fun BrsCrypto.toCryptoUi(): MarketItemUi {
        return MarketItemUi(
            symbol = symbol,
            title = name,
            price = formatCryptoPrice(price),
            unit = unit,
            changeValue = null,
            changePercent = formatPercent(change_percent),
            isPositive = trend(change_percent),
            category = MarketCategory.CRYPTO
        )
    }

    private fun formatNumber(value: Long): String {
        return value
            .toString()
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()
    }

    private fun formatCryptoPrice(value: String): String {
        val number = value.toBigDecimalOrNull()
            ?: return value

        val sign = if (number.signum() < 0) "-" else ""
        val absolute = number.abs()

        val parts = absolute
            .toPlainString()
            .split(".", limit = 2)

        val integerPart = parts[0]
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        return sign +
                integerPart +
                if (parts.size == 2) {
                    ".${parts[1]}"
                } else {
                    ""
                }
    }

    private fun formatPercent(value: Double): String {
        return "%.2f%%".format(value)
    }


    private fun trend(value: Double): Boolean? {
        return when {
            value > 0 -> true
            value < 0 -> false
            else -> null
        }
    }
}