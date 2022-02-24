package com.osisupermoses.currencyconverter.domain.model

import com.osisupermoses.currencyconverter.data.remote.dto.CurrencyDto

object MapperImpl : Mapper {
    override fun toCurrencyModel(
        currencyResponse: CurrencyDto
    ): CurrencyItem {
        return CurrencyItem(
            amount = currencyResponse.amount,
            baseCurrencyCode = currencyResponse.baseCurrencyCode,
            baseCurrencyName = currencyResponse.baseCurrencyName,
            rates = currencyResponse.rates as HashMap<String, Rates>,
            status = currencyResponse.status,
            updatedDate = currencyResponse.updatedDate
        )
    }
}