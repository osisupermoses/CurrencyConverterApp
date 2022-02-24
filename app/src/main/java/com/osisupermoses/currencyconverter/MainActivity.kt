package com.osisupermoses.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.osisupermoses.currencyconverter.screens.home.MainScreen
import com.osisupermoses.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                MainScreen()
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyConverterTheme {
    }
}