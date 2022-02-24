package com.osisupermoses.currencyconverter.screens.home

import androidx.compose.ui.focus.FocusState

sealed class CurrencyEvent {
    data class EnteredText(val value: String) : CurrencyEvent()
    data class ResultText(val value: String) : CurrencyEvent()
    data class OnPickFromCurrencySymbol(val countryCode: String?) : CurrencyEvent()
    data class OnPickToCurrencySymbol(val countryCode: String?) : CurrencyEvent()
    data class DisplayFocus(val focusState: FocusState) : CurrencyEvent()
}
