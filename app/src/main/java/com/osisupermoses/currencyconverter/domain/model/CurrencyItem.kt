package com.osisupermoses.currencyconverter.domain.model

import com.osisupermoses.currencyconverter.data.local.Entity.CurrencyEntity

data class CurrencyItem(
    val baseCurrencyCode: String,
    val baseCurrencyName: String,
    val amount: String,
    var rates: Map<String, Rates>,
    val status: String,
    val updatedDate: String
) {
    fun toCurrencyEntity(): CurrencyEntity {
        return CurrencyEntity(
            baseCurrencyCode = baseCurrencyCode,
            baseCurrencyName = baseCurrencyName,
            amount = amount,
            rates = rates,
            status = status,
            updatedDate = updatedDate
        )
    }
}