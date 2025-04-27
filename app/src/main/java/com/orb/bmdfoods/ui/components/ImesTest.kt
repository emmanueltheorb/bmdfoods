package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ImesTest(
    modifier: Modifier = Modifier
) {
    val textValue = remember { mutableStateOf("") }

    Box(
        modifier.fillMaxSize()
    ) {
        TextField(
            value = textValue.value,
            onValueChange = {
                textValue.value = it
            },
            modifier = modifier.align(Alignment.TopCenter)
        )
        Row(
            modifier
                .fillMaxWidth()
                .height(20.dp)
                .padding(horizontal = 18.dp)
                .padding(bottom = 12.dp)
                .background(color = MaterialTheme.colorScheme.onBackground)
                .imePadding() // Adjust layout for keyboard
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.End
        ) {

        }
    }
}