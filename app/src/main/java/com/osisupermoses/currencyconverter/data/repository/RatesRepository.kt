package com.osisupermoses.currencyconverter.data.repository

import com.osisupermoses.currencyconverter.common.Resource
import com.osisupermoses.currencyconverter.data.local.Entity.CurrencyEntity
import com.osisupermoses.currencyconverter.domain.model.CurrencyItem

interface RatesRepository {

    suspend fun insertAllCurrencyItems(currencyItems: List<CurrencyEntity>)

    suspend fun insertCurrencyItem(currencyItem: CurrencyItem)

    suspend fun deleteCurrencyItem(currencyItem: CurrencyItem)

    suspend fun deleteAllCurrencyItems()

    suspend fun deleteAllCurrencyItems(baseCurrencyCode: String)

    suspend fun getAllCurrencyItems(): List<CurrencyItem>

    suspend fun getAllCurrencyItems(baseCurrencyCode: String): List<CurrencyItem>

    suspend fun getLatestRates(accessKey: String, from: String, to: String, amount: Double): Resource<CurrencyItem>
}