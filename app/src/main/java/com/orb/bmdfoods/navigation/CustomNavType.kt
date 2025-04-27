package com.orb.bmdfoods.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.orb.bmdfoods.data.Foods
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {
    val FoodType = object : NavType<Foods>(
        isNullableAllowed = false
    ) {
        override fun get(
            bundle: Bundle,
            key: String
        ): Foods {
            return Json.decodeFromString(bundle.getString(key) ?: return Foods())
        }

        override fun parseValue(value: String): Foods {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Foods): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: Foods
        ) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}