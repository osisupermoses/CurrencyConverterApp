package com.osisupermoses.currencyconverter.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.osisupermoses.currencyconverter.BuildConfig
import com.osisupermoses.currencyconverter.screens.home.components.CurrencySelector
import com.osisupermoses.currencyconverter.screens.home.components.InputField
import com.osisupermoses.currencyconverter.screens.home.components.SingleSelectDialog
import com.osisupermoses.currencyconverter.screens.home.components.getValidatedNumber
import com.osisupermoses.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val dialogState by lazy { mutableStateOf(false) }

    val state = viewModel.state.value

    val currencySelectorState = remember { mutableStateOf(false) }

    val fromCurrencySymbolState = remember { mutableStateOf("EUR") }
    val toCurrencySymbolState = remember { mutableStateOf("GBP") }

    val currencyList = viewModel.getAllCountrySymbols()

    val resultText = viewModel.resultStr
    val amount = remember { mutableStateOf("") }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFF26E2B6),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { }
                )
                Text(
                    text = "Sign up",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.End,
                    color = Color(0xFF26E2B6),
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.clickable {  }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.padding(start = 30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Currency",
                    color = Color(0xFF0085EA),
                    fontWeight = FontWeight.Bold,
                    fontSize = 50.sp,
                )
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier.offset(y = (-20).dp)
                ) {
                    Text(
                        text = "Calculator",
                        color = Color(0xFF0085EA),
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp
                    )
                    Text(
                        text = ".",
                        color = Color(0xFF26E2B6),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.offset(y = (-8).dp),
                        fontSize = 70.sp,
                    )
                }
            }

            InputField(
                numberText = getValidatedNumber(amount.value),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .height(60.dp)
                    .align(CenterHorizontally),
                enabled = true,
                placeholderText = "",
                currencySymbol = fromCurrencySymbolState.value
            ) {
//                viewModel.onEvent(CurrencyEvent.EnteredText(it))
                amount.value = it
            }
            InputField(
                numberText = resultText.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .height(60.dp)
                    .align(CenterHorizontally),
                enabled = false,
                placeholderText = "",
                currencySymbol = toCurrencySymbolState.value
            ) {
//                viewModel.onEvent(CurrencyEvent.EnteredText(it)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = CenterVertically,
            ) {

                SingleSelectDialog(
                    dialogState = dialogState,
                    title = if (currencySelectorState.value) "Convert From" else "Convert To",
                    optionsList = currencyList,
                    submitButtonText = "Select",
                    onSubmitButtonClick = {
                        if (currencySelectorState.value) {
                            fromCurrencySymbolState.value = currencyList[it]
                        } else {
                            toCurrencySymbolState.value = currencyList[it]
                        }
                    }
                ) {
                    dialogState.value = false
                }
                CurrencySelector(
                    Modifier.width(160.dp),
                    currencySymbol = fromCurrencySymbolState.value,
                    onClick = {
                        dialogState.value = true
                        currencySelectorState.value = true
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.SwapHoriz,
                    contentDescription = "currency swap",
                    tint = Color(0x6F141414),
                    modifier = Modifier
                        .size(40.dp)
                        .align(Top)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CurrencySelector(
                    Modifier.width(160.dp),
                    currencySymbol = toCurrencySymbolState.value,
                    onClick = {
                        dialogState.value = true
                        currencySelectorState.value = false
                    }
                )
            }
            Button(
                onClick = {
//                    if (amount.value.isNotEmpty() && fromCurrencySymbolState.value != toCurrencySymbolState.value) {
//                            if (fromCurrencySymbolState.value == state.currency?.baseCurrencyCode &&
//                                toCurrencySymbolState.value == state.currency.baseCurrencyCode
//                            ) {
                                val accessKey = BuildConfig.API_KEY
                                viewModel.onConvert(
                                    accessKey,
                                    fromCurrencySymbolState.value,
                                    toCurrencySymbolState.value,
                                    amount.value.toDouble()
                                )
//                            }
                        amount.value = ""
//                    }

                },
                colors = buttonColors(Color(0xFF1DD384)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = "Convert",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.button,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Mid-market exchange rate at 13.38 UTC",
                    color = Color(0xFF0085EA),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
                IconButton(
                    onClick = { /*TODO*/ },
//                    modifier = Modifier.fillMaxWidth()

                ) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More Option",
                        tint = Color(0xFF0085EA)
                    )
                }
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