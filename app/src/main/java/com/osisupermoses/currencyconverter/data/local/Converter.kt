package com.osisupermoses.currencyconverter.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.osisupermoses.currencyconverter.data.util.JsonParser
import com.osisupermoses.currencyconverter.domain.model.Rates

@ProvidedTypeConverter
class Converter(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromMeaningsJson(json: String): Rates {
        return jsonParser.fromJson<Rates>(
            json,
            object : TypeToken<Rates>(){}.type
        )!!
    }

    @TypeConverter
    fun toMeaningsJson(meanings: Rates): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<Rates>(){}.type
        ) ?: ""
    }
}