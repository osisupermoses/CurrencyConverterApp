package com.osisupermoses.currencyconverter.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleChoiceView(
    listOptions: List<String> = listOf("EUR", "JPY", "BRL", "GBP")
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(listOptions[1]) }
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                        }
                    )
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { onOptionSelected(text) }
                )
//                Text(
//                    text = text,
//                    style = MaterialTheme.typography.body1.merge(),
//                    modifier = Modifier.padding(start = 8.dp)
//                )
                CurrencySelector(
                    currencySymbol = text
                )
            }
        }
    }
}

@Composable
fun AlertSingleChoiceView(state: MutableState<Boolean>) {
    CommonDialog(title = if(state.value) "Convert From" else "Convert To", state = state) {
        SingleChoiceView()
    }
}


@Composable
fun CommonDialog(
    title: String?,
    state: MutableState<Boolean>,
    content: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            state.value = false
        },
        title = title?.let {
            {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = title)
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                }
            }
        },
        text = content,
        dismissButton = {
            Button(onClick = { state.value = false }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            Button(onClick = { state.value = false }) {
                Text("Ok")
            }
        }, modifier = Modifier.padding(vertical = 8.dp)
    )
}