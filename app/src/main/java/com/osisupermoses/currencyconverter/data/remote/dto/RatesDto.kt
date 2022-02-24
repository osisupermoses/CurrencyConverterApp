package com.osisupermoses.currencyconverter.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.osisupermoses.currencyconverter.domain.model.Rates

@kotlinx.parcelize.Parcelize
data class RatesDto(
    @SerializedName("currency_name")
    val currencyName: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("rate_for_amount")
    val rateForAmount: String
) : Parcelable {
    fun toRatesModel(): Rates {
        return Rates(
            currencyName = currencyName,
            rate = rate,
            rateForAmount = rateForAmount
        )
    }
}