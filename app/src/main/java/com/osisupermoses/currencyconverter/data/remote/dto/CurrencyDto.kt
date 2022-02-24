package com.osisupermoses.currencyconverter.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.osisupermoses.currencyconverter.data.local.Entity.CurrencyEntity
import com.osisupermoses.currencyconverter.domain.model.Rates

@kotlinx.parcelize.Parcelize
data class CurrencyDto(
    @SerializedName("base_currency_code")
    val baseCurrencyCode: String,
    @SerializedName("base_currency_name")
    val baseCurrencyName: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("rates")
    var rates: HashMap<String, RatesDto> = HashMap(),
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_date")
    val updatedDate: String
) : Parcelable {
    fun toCurrencyEntity(): CurrencyEntity {
        return CurrencyEntity(
            baseCurrencyCode = baseCurrencyCode,
            baseCurrencyName = baseCurrencyName,
            amount = amount,
            rates = rates.mapValues { it.value.toRatesModel() } as HashMap<String, Rates>,
            status = status,
            updatedDate = updatedDate
        )
    }
}