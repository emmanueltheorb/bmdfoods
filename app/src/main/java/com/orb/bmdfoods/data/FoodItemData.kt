package com.orb.bmdfoods.data

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.orb.bmdfoods.R
import kotlinx.serialization.Serializable

sealed class SnackBarMessage(
    val message: String,
    val isError: Boolean = false
) {
    class Success(message: String) : SnackBarMessage(message)
    class Error(message: String) : SnackBarMessage(message, isError = true)
}

data class FoodItemState(
    val id: Int,
    @DrawableRes val img: Int,
    val foodName: String,
    val price: Int,
    val availability: Boolean = true,
    val amount: Int?,
    val options: List<OptionState>?
)

@Keep
@IgnoreExtraProperties
@Serializable
data class OptionState(
    val id: Int = 0,
    val name: String = "",
    val price: Int = 0,
    val amount: Int? = null,
    val upperLimit: Int? = null,
    val lowerLimit: Int? = null,
    val mergeGroup: Int? = null,
    val mergeId: Int? = null
)

@IgnoreExtraProperties
@Serializable
data class Foods(
    val id: Int = 0,
    val imgUrl: String = "",
    val foodName: String = "",
    val price: Int = 0,
    val availability: Boolean = true,
    val amount: Int? = null,
    val options: List<OptionState>? = emptyList<OptionState>(),
    val documentId: String = ""
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            foodName,
            " $foodName"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

@IgnoreExtraProperties
@Serializable
data class ReservedFood(
    val imgUrl: String = "",
    val name: String = "",
    val price: Int = 0,
    val quantity: Int = 0,
    val options: List<OptionReserved>? = emptyList<OptionReserved>(),
    val code: String = "00000000",
    val address: String = "",
    val phoneNumber: String = "",
    val documentId: String = "",
    val status: String = "", // Can be: reserved, ordered, received
    val lastStatus: String = "",
    val reservedAt: Long = 0L,
    val userId: String = ""
)

@Keep
@IgnoreExtraProperties
@Serializable
data class OptionReserved(
    val id: Int,
    val name: String,
    val amount: Int?,
    val price: Int
)

val FoodsExample = listOf(
    Foods(
        id = 0,
        imgUrl = "",
        foodName = "Pizzera",
        price = 900,
        amount = null
    ),
    Foods(
        id = 1,
        imgUrl = "",
        foodName = "Wezzy",
        price = 1500,
        amount = null
    )
)

val FoodExample = Foods(
    id = 1,
    imgUrl = "",
    foodName = "Eba and soup",
    price = 2500,
    amount = null,
    options = listOf(
        OptionState(
            id = 0,
            name = "Egusi soup",
            price = 400,
            amount = null,
            upperLimit = null,
            lowerLimit = null,
            mergeGroup = 0,
            mergeId = 0
        ),
        OptionState(
            id = 1,
            name = "Banga soup",
            price = 400,
            amount = null,
            upperLimit = null,
            lowerLimit = null,
            mergeGroup = 0,
            mergeId = 1
        ),
        OptionState(
            id = 2,
            name = "Vegetable soup",
            price = 600,
            amount = null,
            upperLimit = null,
            lowerLimit = null,
            mergeGroup = 0,
            mergeId = 2
        ),
        OptionState(
            id = 3,
            name = "Eba",
            price = 300,
            amount = 1,
            upperLimit = 5,
            lowerLimit = 1,
            mergeGroup = 1,
            mergeId = 0
        ),
        OptionState(
            id = 4,
            name = "Fufu",
            price = 300,
            amount = 1,
            upperLimit = 5,
            lowerLimit = 1,
            mergeGroup = 1,
            mergeId = 1
        ),
        OptionState(
            id = 5,
            name = "Turkey",
            price = 1600,
            amount = 1,
            upperLimit = 2,
            lowerLimit = 1,
            mergeGroup = 2,
            mergeId = 0
        ),
        OptionState(
            id = 6,
            name = "Chicken",
            price = 1400,
            amount = 1,
            upperLimit = 2,
            lowerLimit = 1,
            mergeGroup = 2,
            mergeId = 1
        ),
        OptionState(
            id = 7,
            name = "Goat meat",
            price = 1500,
            amount = 1,
            upperLimit = 2,
            lowerLimit = 1,
            mergeGroup = 2,
            mergeId = 2
        ),
        OptionState(
            id = 8,
            name = "Kpomo",
            price = 500,
            amount = 1,
            upperLimit = 3,
            lowerLimit = 1,
            mergeGroup = 2,
            mergeId = 3
        ),
        OptionState(
            id = 9,
            name = "Fish",
            price = 900,
            amount = 1,
            upperLimit = 2,
            lowerLimit = 1,
            mergeGroup = 2,
            mergeId = 4
        )
    )
)

val ReservedFoodExample = ReservedFood(
    imgUrl = "",
    name = "Eba and soup",
    price = 2600,
    quantity = 1,
    options = listOf(
        OptionReserved(
            id = 0,
            name = "Fufu",
            amount = 3,
            price = 1200
        ),
        OptionReserved(
            id = 0,
            name = "Eba",
            amount = 3,
            price = 1050
        )
    ),
    code = "12340987",
    address = "Transformer Street, Old emede road",
    phoneNumber = "08125676654",
    documentId = ""
)

val OptionsReservedExample = listOf(
    OptionReserved(
        id = 0,
        name = "Fufu",
        amount = 3,
        price = 1200
    ),
    OptionReserved(
        id = 0,
        name = "Eba",
        amount = 3,
        price = 1050
    )
)

val FoodItemsData = mutableListOf(
    FoodItemState(
        id = 0,
        img = R.drawable.pizza,
        foodName = "Pizza",
        price = 2000,
        amount = 15,
        options = null
    ),
    FoodItemState(
        id = 1,
        img = R.drawable.eba_and_soup,
        foodName = "Eba and soup",
        price = 2500,
        amount = null,
        options = listOf(
            OptionState(
                id = 0,
                name = "Egusi soup",
                price = 400,
                amount = null,
                upperLimit = null,
                lowerLimit = null,
                mergeGroup = 0,
                mergeId = 0
            ),
            OptionState(
                id = 1,
                name = "Banga soup",
                price = 400,
                amount = null,
                upperLimit = null,
                lowerLimit = null,
                mergeGroup = 0,
                mergeId = 1
            ),
            OptionState(
                id = 2,
                name = "Vegetable soup",
                price = 600,
                amount = null,
                upperLimit = null,
                lowerLimit = null,
                mergeGroup = 0,
                mergeId = 2
            ),
            OptionState(
                id = 3,
                name = "Eba",
                price = 300,
                amount = 1,
                upperLimit = 5,
                lowerLimit = 1,
                mergeGroup = 1,
                mergeId = 0
            ),
            OptionState(
                id = 4,
                name = "Fufu",
                price = 300,
                amount = 1,
                upperLimit = 5,
                lowerLimit = 1,
                mergeGroup = 1,
                mergeId = 1
            ),
            OptionState(
                id = 5,
                name = "Turkey",
                price = 1600,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 2,
                mergeId = 0
            ),
            OptionState(
                id = 6,
                name = "Chicken",
                price = 1400,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 2,
                mergeId = 1
            ),
            OptionState(
                id = 7,
                name = "Goat meat",
                price = 1500,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 2,
                mergeId = 2
            ),
            OptionState(
                id = 8,
                name = "Kpomo",
                price = 500,
                amount = 1,
                upperLimit = 3,
                lowerLimit = 1,
                mergeGroup = 2,
                mergeId = 3
            ),
            OptionState(
                id = 9,
                name = "Fish",
                price = 900,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 2,
                mergeId = 4
            )
        )
    ),
    FoodItemState(
        id = 2,
        img = R.drawable.meat_pie,
        foodName = "Meat pie",
        price = 1500,
        amount = 6,
        options = null
    ),
    FoodItemState(
        id = 3,
        img = R.drawable.eba_and_stew,
        foodName = "Eba and stew",
        price = 2500,
        amount = null,
        availability = false,
        options = listOf(
            OptionState(
                id = 0,
                name = "Stew",
                price = 400,
                amount = null,
                upperLimit = null,
                lowerLimit = null
            ),
            OptionState(
                id = 1,
                name = "Eba",
                price = 300,
                amount = 1,
                upperLimit = 5,
                lowerLimit = 1,
                mergeGroup = 0,
                mergeId = 0
            ),
            OptionState(
                id = 2,
                name = "Fufu",
                price = 300,
                amount = 1,
                upperLimit = 5,
                lowerLimit = 1,
                mergeGroup = 0,
                mergeId = 1
            ),
            OptionState(
                id = 3,
                name = "Turkey",
                price = 1600,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 1,
                mergeId = 0
            ),
            OptionState(
                id = 4,
                name = "Chicken",
                price = 1400,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 1,
                mergeId = 1
            ),
            OptionState(
                id = 5,
                name = "Goat meat",
                price = 1500,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 1,
                mergeId = 2
            ),
            OptionState(
                id = 6,
                name = "Kpomo",
                price = 500,
                amount = 1,
                upperLimit = 3,
                lowerLimit = 1,
                mergeGroup = 1,
                mergeId = 3
            ),
            OptionState(
                id = 7,
                name = "Fish",
                price = 900,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1,
                mergeGroup = 1,
                mergeId = 4
            ),
            OptionState(
                id = 8,
                name = "Test Fish",
                price = 300,
                amount = 1,
                upperLimit = 2,
                lowerLimit = 1
            )
        )
    ),
    FoodItemState(
        id = 4,
        img = R.drawable.hot_dogs,
        foodName = "Hot dogs",
        price = 900,
        amount = 4,
        options = null
    ),
    FoodItemState(
        id = 5,
        img = R.drawable.ice_cream_plate,
        foodName = "Ice cream",
        price = 1500,
        amount = 25,
        options = listOf(
            OptionState(
                id = 0,
                name = "Ice cream plate",
                price = 1500,
                amount = 1,
                upperLimit = null,//6
                lowerLimit = null,//1
                mergeGroup = 0,
                mergeId = 0
            ),
            OptionState(
                id = 1,
                name = "Ice cream medium cup",
                price = 1000,
                amount = 1,
                upperLimit = null,//6
                lowerLimit = null,//1
                mergeGroup = 0,
                mergeId = 1
            ),
            OptionState(
                id = 2,
                name = "Ice cream small cup",
                price = 700,
                amount = 1,
                upperLimit = null,//6
                lowerLimit = null,//1
                mergeGroup = 0,
                mergeId = 2
            ),
            OptionState(
                id = 3,
                name = "Chocolate Flavor",
                price = 0,
                amount = null,
                upperLimit = null,
                lowerLimit = null,
                mergeGroup = 1,
                mergeId = 0
            ),
            OptionState(
                id = 4,
                name = "Vanilla Flavor",
                price = 0,
                amount = null,
                upperLimit = null,
                lowerLimit = null,
                mergeGroup = 1,
                mergeId = 1
            ),
            OptionState(
                id = 5,
                name = "StrawBerry Flavor",
                price = 0,
                amount = null,
                upperLimit = null,
                lowerLimit = null,
                mergeGroup = 1,
                mergeId = 2
            ),
            OptionState(
                id = 6,
                name = "Banana Flavor",
                price = 0,
                amount = null,
                upperLimit = null,
                lowerLimit = null,
                mergeGroup = 1,
                mergeId = 3
            )
        )
    )
)
