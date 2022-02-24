package com.osisupermoses.currencyconverter.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.osisupermoses.currencyconverter.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencySelector(
    modifier: Modifier = Modifier,
    currencySymbol: String = "EUR",
    onClick: () -> Unit = {}
) {
    val painter = rememberImagePainter(data = R.drawable.ic_launcher_background) {
        error(R.drawable.ic_launcher_background)
        placeholder(R.drawable.ic_launcher_background)
    }

    Card(
        modifier = modifier
            .height(50.dp)
            .border(
                border = BorderStroke(1.dp, Color(0x284E5050)),
                shape = RoundedCornerShape(5.dp)
            ),
        elevation = 0.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(modifier = Modifier
                .size(20.dp),
                shape = CircleShape,
                elevation = 2.dp) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = "Currency Country Image",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = currencySymbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0x6F141414),
                style = MaterialTheme.typography.body1

            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Down Arrow",
                modifier = Modifier
                    .size(30.dp),
                tint = Color.DarkGray)
        }
    }
}

