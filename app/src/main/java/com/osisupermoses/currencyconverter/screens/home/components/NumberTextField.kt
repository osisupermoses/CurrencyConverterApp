package com.osisupermoses.currencyconverter.screens.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    numberText: String = "",
    currencySymbol: String = "",
    maxLine: Int = 1,
    enabled: Boolean,
    placeholderText: String,
    onImeAction: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = numberText,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0x23C0C8C8),
            focusedIndicatorColor =  Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        maxLines = maxLine,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
                keyboardController?.hide()
            }
        ),
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        },
        enabled = enabled,
        modifier = modifier,
        trailingIcon = {
            Text(
                text = currencySymbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(end = 20.dp, top = 10.dp, bottom = 10.dp)
            )
        }
    )
}

fun getValidatedNumber(text: String): String {
    // Start by filtering out unwanted characters like commas and multiple decimals
    val filteredChars = text.filterIndexed { index, c ->
        c in "0123456789" ||                      // Take all digits
                (c == '.' && text.indexOf('.') == index)  // Take only the first decimal
    }
    // Now we need to remove extra digits from the input
    return if (filteredChars.contains('.')) {
        val beforeDecimal = filteredChars.substringBefore('.')
        val afterDecimal = filteredChars.substringAfter('.')
        beforeDecimal.take(12) + "." + afterDecimal.take(6)    // If decimal is present, take first 3 digits before decimal and first 2 digits after decimal
    } else {
        filteredChars.take(12)     // If there is no decimal, just take the first 3 digits
    }
}