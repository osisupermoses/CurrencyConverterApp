package com.osisupermoses.currencyconverter.domain.model

import com.osisupermoses.currencyconverter.data.remote.dto.CurrencyDto

interface Mapper {
    fun toCurrencyModel(
        currencyResponse: CurrencyDto
    ): CurrencyItem
}