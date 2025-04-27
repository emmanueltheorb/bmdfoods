package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    onInputChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
) {
    val textValue = remember { mutableStateOf("") }

    BasicTextField(
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onInputChange(it)
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box {
                if (textValue.value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
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