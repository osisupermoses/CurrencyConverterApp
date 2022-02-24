package com.osisupermoses.currencyconverter.screens.home

import com.osisupermoses.currencyconverter.domain.model.CurrencyItem
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osisupermoses.currencyconverter.common.Resource
import com.osisupermoses.currencyconverter.data.repository.RatesRepository
import com.osisupermoses.currencyconverter.domain.model.Rates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var repository: RatesRepository
) : ViewModel() {

    private val _state = mutableStateOf(CurrencyResponseState())
    val state: State<CurrencyResponseState> = _state

    private val _resultStr = mutableStateOf("")
    val resultStr: State<String> = _resultStr

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

//    private var searchJob: Job? = null


//    fun onEvent(event: CurrencyEvent) {
//        when (event) {
//            is CurrencyEvent.EnteredText -> {
//                _amount.value = event.value
//            }
//           is CurrencyEvent.ResultText -> {
//
//           }
//        }
//    }

    fun getAllCountrySymbols(): List<String> {
        val allCurrencySymbols = ArrayList<String>()
        for (i in getAllCountries()) {
            val currencySymbol = getSymbol(getCountryCode(i))
            if (currencySymbol != null)
                allCurrencySymbols.add(currencySymbol)
        }
        return allCurrencySymbols.distinct().filter { it.isNotBlank() }
    }

    fun onConvert(
        accessKey: String,
        from: String,
        to: String,
        amount: Double
    ) {
//        searchJob?.cancel()
//        searchJob =
        val currencyList: ArrayList<CurrencyItem> = ArrayList()
            viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getLatestRates(accessKey, from, to, amount)) {
                    is Resource.Success -> {
                        if (result.data?.status == "success") {
                            val map: Map<String, Rates> = result.data.rates
                            map.keys.forEach {
                                val rateForAmount = map[it]?.rateForAmount
                                _resultStr.value = rateForAmount!!
//                                Log.e("RESULT", "Result = ${_resultStr.value}}")
                                if (from.isNotEmpty() && to.isNotEmpty() && amount.toString().isNotEmpty()) {
                                     currencyList.add(
                                         CurrencyItem(
                                             baseCurrencyCode = result.data.baseCurrencyCode,
                                             baseCurrencyName = result.data.baseCurrencyName,
                                             updatedDate = result.data.updatedDate,
                                             amount = result.data.amount,
                                             rates = map,
                                             status = result.data.status
                                         )
                                     )
                                    if (currencyList.isNotEmpty()) {
                                        deleteAllCurrencyRates(from)
                                        insertCurrencyRatesIntoDb(currencyList)
                                    }

                                }
                            }
                        } else if(result.data?.status == "fail") {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    result.message!!
                                )
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                }
            }
    }

    private fun deleteAllCurrencyRates(baseCurrencyCode: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCurrencyItems(baseCurrencyCode)
    }

    private fun insertCurrencyRatesIntoDb(currencyItems: List<CurrencyItem>) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAllCurrencyItems(currencyItems.map { it.toCurrencyEntity() })
    }

    /**
     * A method for getting a country's currency symbol from the country's code
     * e.g USA - USD
     */

    private fun getSymbol(countryCode: String?): String? {
        val availableLocales = Locale.getAvailableLocales()
        for (i in availableLocales.indices) {
            if (availableLocales[i].country == countryCode
            ) return Currency.getInstance(availableLocales[i]).currencyCode
        }
        return ""
    }

    /**
     * A method for getting a country's code from the country name
     * e.g Nigeria - NG
     */

    private fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }

    /**
     * A method for getting all countries in the world - about 256 or so
     */

    private fun getAllCountries(): ArrayList<String> {

        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()

        return countries
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
    }
}