package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orb.bmdfoods.ui.theme.AppTheme
import com.orb.bmdfoods.ui.theme.RobotoFontFamily

@Composable
fun MyOutlinedTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    width: Dp = 240.dp,
    heightIn: Dp = 40.dp,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val textValue = remember { mutableStateOf("") }

    Card(
        modifier
            .width(width)
            .heightIn(heightIn),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.scrim
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(6.dp)
    ) {
        BasicTextField(
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(6.dp)
            ,
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.labelMedium,
            maxLines = 2,
            cursorBrush = SolidColor(
                MaterialTheme.colorScheme.onBackground
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (textValue.value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    innerTextField()
                }
            },
            keyboardOptions = keyboardOptions
        )
    }
}

@Composable
fun MyLoginTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    width: Dp = 260.dp,
    height: Dp = 60.dp,
    textValue: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedCard(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(width)
                .height(height)
                .padding(5.dp)
        ) {
            BasicTextField(
                value = textValue,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = RobotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                maxLines = 1,
                cursorBrush = SolidColor(
                    MaterialTheme.colorScheme.onBackground
                ),
                visualTransformation = visualTransformation,
                decorationBox = { innerTextField ->
                    Box {
                        if (textValue.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                },
                keyboardOptions = keyboardOptions
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrevMyOutlinedTextField() {
    AppTheme {
        MyOutlinedTextField(
            placeholder = "order address",
            onValueChange = {}
        )
    }
}