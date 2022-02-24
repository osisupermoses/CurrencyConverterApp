package com.osisupermoses.currencyconverter.domain.model

data class Rates(
    val currencyName: String,
    val rate: String,
    val rateForAmount: String
)
