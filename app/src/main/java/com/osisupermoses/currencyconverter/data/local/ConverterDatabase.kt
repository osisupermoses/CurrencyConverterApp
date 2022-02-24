package com.osisupermoses.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.osisupermoses.currencyconverter.data.local.Entity.CurrencyEntity


@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
@TypeConverters(MapConverter::class)
abstract class ConverterDatabase: RoomDatabase() {

    abstract val dao: RateDao

    companion object {
        const val DATABASE = "converter_db"
    }
}
