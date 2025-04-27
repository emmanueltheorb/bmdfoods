package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orb.bmdfoods.R

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    onSearchButtonClicked: () -> Unit
) {
    Surface(
        modifier = modifier
            .size(40.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable(onClick = onSearchButtonClicked)
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = modifier.size(34.dp)
            )
        }
    }
//    Box(
//        modifier = modifier
//            .size(40.dp)
//            .background(
//                color = MaterialTheme.colorScheme.surface,
//                shape = RoundedCornerShape(12.dp)
//            ),
//        contentAlignment = Alignment.Center,
//    ) {
//        Icon(
//            painter = painterResource(id = R.drawable.ic_search),
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.onSurface,
//            modifier = modifier.padding(14.dp)
//        )
//    }
}
