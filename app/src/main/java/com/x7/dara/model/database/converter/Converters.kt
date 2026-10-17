package com.x7.dara.model.database.converter

import androidx.room.TypeConverter
import com.x7.dara.model.database.account.AccountType
import com.x7.dara.model.database.asset.AssetType
import com.x7.dara.model.database.transaction.TransactionType

class Converters {

    @TypeConverter
    fun fromAccountType(type: AccountType): String {
        return type.name
    }

    @TypeConverter
    fun toAccountType(value: String): AccountType {
        return AccountType.valueOf(value)
    }


    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }


    @TypeConverter
    fun fromAssetType(type: AssetType): String {
        return type.name
    }

    @TypeConverter
    fun toAssetType(value: String): AssetType {
        return AssetType.valueOf(value)
    }
}