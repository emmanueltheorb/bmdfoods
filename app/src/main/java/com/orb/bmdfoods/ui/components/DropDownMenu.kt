package com.orb.bmdfoods.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.orb.bmdfoods.R
import com.orb.bmdfoods.data.OptionState
import com.orb.bmdfoods.ui.sections.createNameList


@Composable
fun DropdownMenuSelector(
    modifier: Modifier = Modifier,
    options: List<OptionState>,
    mergeGroupNumber: Int,
    selectedIndex: Int,  // Hoisted state
    onSelectionChange: (Int) -> Unit,  // Callback for parent
    width: Dp = 110.dp
) {
    val nameList = remember(options, mergeGroupNumber) {
        createNameList(options, mergeGroupNumber)
    }

    var isExpanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier.padding(16.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        )
    ) {
        Row(
            modifier = Modifier
                .width(width)
                .height(50.dp)
                .padding(5.dp)
                .clickable { isExpanded = true },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = nameList.getOrNull(selectedIndex) ?: "Select",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Image(
                painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                contentDescription = "Dropdown arrow"
            )
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            nameList.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        onSelectionChange(index)
                        isExpanded = false
                    },
                    colors = MenuItemColors(
                        textColor = MaterialTheme.colorScheme.onBackground,
                        leadingIconColor = MaterialTheme.colorScheme.onBackground,
                        trailingIconColor = MaterialTheme.colorScheme.onBackground,
                        disabledTextColor = MaterialTheme.colorScheme.onBackground,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}