package com.osisupermoses.currencyconverter.data.local

import com.osisupermoses.currencyconverter.common.Resource
import com.osisupermoses.currencyconverter.data.local.Entity.CurrencyEntity
import com.osisupermoses.currencyconverter.data.remote.ConverterApiService
import com.osisupermoses.currencyconverter.data.repository.RatesRepository
import com.osisupermoses.currencyconverter.domain.model.CurrencyItem
import retrofit2.HttpException
import java.io.IOException

class RatesRepositoryImpl(
    private val api: ConverterApiService,
    private val dao: RateDao
): RatesRepository {
    override suspend fun insertAllCurrencyItems(currencyItems: List<CurrencyEntity>) {
        dao.insertCurrencyItems(currencyItems)
    }

    override suspend fun insertCurrencyItem(currencyItem: CurrencyItem) {
        dao.insertCurrencyItem(currencyItem.toCurrencyEntity())
    }

    override suspend fun deleteCurrencyItem(currencyItem: CurrencyItem) {
        dao.deleteCurrencyItem(currencyItem.toCurrencyEntity())
    }

    override suspend fun deleteAllCurrencyItems() {
        dao.deleteAllCurrencyItems()
    }

    override suspend fun deleteAllCurrencyItems(baseCurrencyCode: String) {
        dao.deleteAllCurrencyItems(baseCurrencyCode)
    }

    override suspend fun getAllCurrencyItems(): List<CurrencyItem> {
        return getAllCurrencyItems()
    }

    override suspend fun getAllCurrencyItems(baseCurrencyCode: String): List<CurrencyItem> {
        return getAllCurrencyItems(baseCurrencyCode)
    }

    override suspend fun getLatestRates(accessKey: String, from: String, to: String, amount: Double): Resource<CurrencyItem> {
        Resource.Loading(data = false)
        return try {
            val remoteResponse = api.convertCurrency(accessKey, from, to, amount)
            val result = remoteResponse.body()
            result!!.toCurrencyEntity().toCurrencyModel().let {
                return@let Resource.Success(it)
            }
        } catch (e: HttpException) {
            return Resource.Error(
                message = "Oops, something went wrong!",
                data = null
            )
        } catch (e: IOException) {
            return Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = null
            )
        }
    }
}