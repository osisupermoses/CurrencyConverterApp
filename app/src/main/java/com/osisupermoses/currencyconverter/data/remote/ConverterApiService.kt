package com.osisupermoses.currencyconverter.data.remote

import com.osisupermoses.currencyconverter.BuildConfig
import com.osisupermoses.currencyconverter.data.remote.dto.CurrencyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ConverterApiService {

    @GET("convert")
    suspend fun convertCurrency(
        @Query("api_key") accessKey: String = API_KEY,
        @Query("from") from: String = "EUR",
        @Query("to") to: String = "GBP",
        @Query("amount") amount: Double = 10.000
    ): Response<CurrencyDto>


    companion object {

        //Base URL
        const val BASE_URL = "https://api.getgeoapi.com/v2/currency/"

        //API KEY - Go to geo currency converter website, obtain an API key and paste it between " "
        const val API_KEY = BuildConfig.API_KEY

        //COVERT URL
        const val CONVERT_URL = "convert"

    }
}

