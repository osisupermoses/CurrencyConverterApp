package com.osisupermoses.currencyconverter.data.local

import androidx.room.*
import com.osisupermoses.currencyconverter.data.local.Entity.CurrencyEntity

@Dao
interface RateDao {

    @Query("SELECT * FROM currencyentity WHERE baseCurrencyCode = :symbol")
    fun getCurrencyItem(symbol: String): CurrencyEntity

    @Query("SELECT * FROM currencyEntity")
    fun getAllCurrencyItems(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyItems(currencyItems: List<CurrencyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyItem(currencyItem: CurrencyEntity)

    @Query("DELETE FROM currencyentity WHERE baseCurrencyCode IN(:symbol)")
    suspend fun deleteAllCurrencyItems(symbol: String)

    @Query("DELETE FROM CurrencyEntity")
    suspend fun deleteAllCurrencyItems()

    @Delete
    suspend fun deleteCurrencyItem(currencyItem: CurrencyEntity)

}