package com.osisupermoses.currencyconverter.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.osisupermoses.currencyconverter.data.local.ConverterDatabase
import com.osisupermoses.currencyconverter.data.local.Converter
import com.osisupermoses.currencyconverter.data.local.MapConverter
import com.osisupermoses.currencyconverter.data.local.RatesRepositoryImpl
import com.osisupermoses.currencyconverter.data.remote.ConverterApiService
import com.osisupermoses.currencyconverter.data.repository.RatesRepository
import com.osisupermoses.currencyconverter.data.util.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConverterApiService(): ConverterApiService {
        return Retrofit.Builder()
            .baseUrl(ConverterApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConverterApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRatesRepository(
        apiService: ConverterApiService,
        db: ConverterDatabase
    ): RatesRepository {
        return RatesRepositoryImpl(apiService, db.dao)
    }

    @Provides
    @Singleton
    fun provideConverterDatabase(app: Application): ConverterDatabase {
        return Room.databaseBuilder(
            app,
            ConverterDatabase::class.java,
            ConverterDatabase.DATABASE)
            .addTypeConverter(MapConverter())
            .build()
    }
}