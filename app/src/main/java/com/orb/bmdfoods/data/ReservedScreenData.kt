package com.orb.bmdfoods.data

import androidx.annotation.DrawableRes

data class ReservedScreenState(
    val id: Int,
    @DrawableRes val img: Int,
    val name: String,
    val price: Int,
    val options: MutableList<OptionState>?,
    val startClock: Boolean = false
)