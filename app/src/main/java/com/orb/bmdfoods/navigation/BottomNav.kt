package com.orb.bmdfoods.navigation

import com.orb.bmdfoods.R

data class BottomNavItem(
    val title: String? = null,
    val dest: Dest,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val hasNews: Boolean = false,
    val badges: Int? = null
)

val bottomNavIcons = listOf(
    BottomNavItem(
        selectedIcon = R.drawable.home_filled,
        unselectedIcon = R.drawable.home_icon,
        dest = Dest.AvailableFoodsScreen
    ),
    BottomNavItem(
        selectedIcon = R.drawable.stack_icon,
        unselectedIcon = R.drawable.stack_icon,
        dest = Dest.OrdersScreen
    ),
    BottomNavItem(
        selectedIcon = R.drawable.user_icon_filled,
        unselectedIcon = R.drawable.user_icon,
        dest = Dest.ProfileScreen
    )
)