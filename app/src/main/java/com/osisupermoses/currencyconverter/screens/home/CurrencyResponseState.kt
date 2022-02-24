package com.osisupermoses.currencyconverter.screens.home

import com.osisupermoses.currencyconverter.domain.model.CurrencyItem

data class CurrencyResponseState(
    val resultText: String = "Initial state of the result",
    val currencyItem: CurrencyItem? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
