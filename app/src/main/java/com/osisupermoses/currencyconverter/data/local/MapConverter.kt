package com.osisupermoses.currencyconverter.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.osisupermoses.currencyconverter.domain.model.Rates

@ProvidedTypeConverter
class MapConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, Rates> {
        val mapType = object : TypeToken<Map<String, Rates>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String, Rates>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}