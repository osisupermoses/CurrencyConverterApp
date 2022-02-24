package com.osisupermoses.currencyconverter.screens.home.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


// The state should be at the lower most common parent of the
// composables, which are going to interact.
//val dialogState by lazy { mutableStateOf(false) }
//val selectedCountry by lazy { mutableStateOf("") }

//@Composable
//fun SingleChoiceDialogActivityContent() {
//    Column {
//        Button(
//            onClick = {
//                dialogState.value = true
//            },
//            modifier = Modifier.padding(16.dp).fillMaxWidth(),
//            shape = RoundedCornerShape(8.dp)
//        ) {
//            Text(
//                text = "Show Countries List",
//                textAlign = TextAlign.Center,
//                color = Color.White
//            )
//        }
//        Divider(color = Color.Black)
//        Text(
//            text = selectedCountry.value,
//            textAlign = TextAlign.Center,
//            color = Color.Black
//        )
//    }
//}

// Generally it's a good habit that we create 2 composables, one which accepts
// state and one which maintain its own state. That way,
// the developer has more control ,which approach he/she wants to follow.
@Composable
fun SingleSelectDialog(
    title: String,
    dialogState: MutableState<Boolean>,
    optionsList: List<String>,
    defaultSelected: Int = -1,
    submitButtonText: String,
    onSubmitButtonClick: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (dialogState.value) {
        SingleSelectDialog(
            title = title,
            optionsList = optionsList,
            defaultSelected = defaultSelected,
            submitButtonText = submitButtonText,
            onSubmitButton = onSubmitButtonClick,
            onDismissRequest = onDismissRequest
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun SingleSelectDialog(
    title: String,
    optionsList: List<String>,
    defaultSelected: Int = -1,
    submitButtonText: String,
    onSubmitButton: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val selectedOption = mutableStateOf(defaultSelected)

    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Surface(
            modifier = Modifier.width(300.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = title)
                Divider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier.width(500.dp)
                ) {
                    items(optionsList) {
                        val selected = if (selectedOption.value == -1) {
                            ""
                        } else {
                            optionsList[selectedOption.value]
                        }
                        RadioButton(it, selected) { selectedValue ->
                            selectedOption.value = optionsList.indexOf(selectedValue)
                            if (selectedOption.value == -1) {
                                return@RadioButton
                            } else {
                                onSubmitButton(selectedOption.value)
                                onDismissRequest.invoke()
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
//                Button(
//                    onClick = {
//                        if (selectedOption.value == -1) {
//                            return@Button
//                        }
//                        onSubmitButtonClick.invoke(selectedOption.value)
//                        onDismissRequest.invoke()
//                    },
//                    shape = MaterialTheme.shapes.medium,
//                ) {
//                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
//                        Text(text = submitButtonText)
//                    }
//                }
            }

        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun RadioButton(
    text: String,
    selectedValue: String,
    onClickListener: (String) -> Unit
) {

    Row(Modifier
        .fillMaxWidth()
        .selectable(
            selected = (text == selectedValue),
            onClick = {
                onClickListener(text)
            }
        )
        .padding(horizontal = 5.dp),
        verticalAlignment = CenterVertically
    ) {
        // The Default Radio Button in Jetpack Compose doesn't accept text as an argument.
        // So have Text Composable to show text.
        RadioButton(
            selected = (text == selectedValue),
            onClick = {
                onClickListener(text)
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF0085EA)
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body1.merge(),
        )
    }
}