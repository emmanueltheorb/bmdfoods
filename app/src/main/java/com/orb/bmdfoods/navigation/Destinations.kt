package com.orb.bmdfoods.navigation

import com.orb.bmdfoods.data.Foods
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class ReservingScreen(
    val food: Foods
)

@Serializable
object ReservedScreen

sealed class SubGraph {

    @Serializable
    data object Auth: SubGraph()

    @Serializable
    data object Home: SubGraph()
}

sealed class Dest {

    @Serializable
    data object SplashScreen: Dest()

    @Serializable
    data object LoginScreen: Dest()

    @Serializable
    data object SignUpScreen: Dest()

    @Serializable
    data object AvailableFoodsScreen: Dest()

    @Serializable
    data object SearchScreen: Dest()

    @Serializable
    data object OrdersScreen: Dest()

    @Serializable
    data object ReservingProcessScreen: Dest()

    @Serializable
    data object OrderingProcessScreen: Dest()

    @Serializable
    data object ProfileScreen: Dest()

    @Serializable
    data class ReservingScreen(
        val food: Foods,
        val imgUrl: String
    ): Dest()
}