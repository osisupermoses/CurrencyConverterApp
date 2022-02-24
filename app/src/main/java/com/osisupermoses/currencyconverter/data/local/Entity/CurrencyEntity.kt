package com.osisupermoses.currencyconverter.data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.osisupermoses.currencyconverter.data.local.MapConverter
import com.osisupermoses.currencyconverter.domain.model.CurrencyItem
import com.osisupermoses.currencyconverter.domain.model.Rates

@Entity
data class CurrencyEntity(
    val baseCurrencyCode: String,
    val baseCurrencyName: String,
    val amount: String,
    @TypeConverters(MapConverter::class)
    val rates: Map<String, Rates>,
    val status: String,
    val updatedDate: String,
    @PrimaryKey val id: Int? = null,
) {
    fun toCurrencyModel(): CurrencyItem {
        return CurrencyItem(
            baseCurrencyCode = baseCurrencyCode,
            baseCurrencyName = baseCurrencyName,
            amount = amount,
            rates = rates,
            status = status,
            updatedDate = updatedDate
        )
    }
}