package com.orb.bmdfoods.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orb.bmdfoods.ui.theme.AppTheme
import com.orb.bmdfoods.ui.theme.SurfaceVariant
import com.orb.bmdfoods.ui.theme.SurfaceVariantLight

@Composable
fun Messages (
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    onChatClicked: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 10.dp, start = 18.dp, end = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Messages",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier.height(15.dp))
        Chat(
            text = "message bmd employees",
            onChatClicked = onChatClicked
        )
        Spacer(modifier.height(10.dp))
        Chat(
            text = "message app developer",
            onChatClicked = onChatClicked
        )
    }
}

@Composable
fun Chat(
    modifier: Modifier = Modifier,
    text: String,
    onChatClicked: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onChatClicked
            ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.5.dp, color = SurfaceVariantLight),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Text(
            modifier = modifier.padding(8.dp),
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun PrevMessages() {
    AppTheme {
        Messages(
            onNavigate = {},
            onChatClicked = {}
        )
    }
}