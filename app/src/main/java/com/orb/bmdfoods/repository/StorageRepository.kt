package com.orb.bmdfoods.repository

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.net.Uri
import com.google.firebase.auth.ktx.auth
//import com.google.firebase.auth.ktx.auth
//import com.orb.bmdfoods.data.Foods
import com.orb.bmdfoods.data.OptionState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.orb.bmdfoods.data.Foods
import com.orb.bmdfoods.data.OptionReserved
import com.orb.bmdfoods.data.ReservedFood
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import kotlin.Long
import kotlin.random.Random

const val FOODS_NODE = "foods"
const val RESERVES_NODE = "reserves"
const val USERS_RESERVES_NODE = "usersFoods"

class StorageRepository @Inject constructor() {
    val database = Firebase.database.reference
    private val foodsRef = database.child(FOODS_NODE)
    private val reservesRef = database.child(RESERVES_NODE)
    private val usersFoodsRef = database.child(USERS_RESERVES_NODE)

    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null
    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    fun getFoodList(): Flow<Resources<List<Foods>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foods = if (snapshot.exists()) {
                    snapshot.children.mapNotNull { foodSnapshot ->
                        foodSnapshot.getValue(Foods::class.java)?.copy(
                            documentId = foodSnapshot.key ?: ""
                        )
                    }.sortedByDescending { it.id }
                } else {
                    emptyList()
                }
                trySend(Resources.Success(foods))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resources.Error(error.toException()))
            }
        }

        foodsRef.addValueEventListener(listener)
        awaitClose { foodsRef.removeEventListener(listener) }
    }

    fun getUserOrders(): Flow<Resources<List<ReservedFood>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foods = if (snapshot.exists()) {
                    snapshot.children.mapNotNull { foodSnapshot ->
                        foodSnapshot.getValue(ReservedFood::class.java)?.copy(
                            documentId = foodSnapshot.key ?: ""
                        )
                    }.sortedByDescending { it.reservedAt }
                } else {
                    emptyList()
                }
                trySend(Resources.Success(foods))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resources.Error(error.toException()))
            }
        }

        usersFoodsRef.child(getUserId()).addValueEventListener(listener)
        awaitClose { usersFoodsRef.removeEventListener(listener) }
    }

    fun reserveFood(
        food: ReservedFood,
        onComplete: (Boolean) -> Unit,
        error: (String) -> Unit
    ) {
        // Generate a unique key for both paths
        val foodKey = usersFoodsRef.child(user()?.uid ?: "").push().key
            ?: run {
                error("Couldn't generate food ID")
                return
            }

        val randomCode = Random.nextInt(from = 0, 99999999)

        val foodMap = hashMapOf(
            "imgUrl" to food.imgUrl,
            "name" to food.name,
            "price" to food.price,
            "quantity" to food.quantity,
            "options" to food.options,
            "code" to randomCode.toString(),
            "address" to food.address,
            "phoneNumber" to food.phoneNumber,
            "documentId" to foodKey,
            "status" to "reserved",
            "lastStatus" to food.lastStatus,
            "reservedAt" to ServerValue.TIMESTAMP,
            "userId" to user()?.uid
        )

        // Create multi-location update paths
        val updates = hashMapOf<String, Any>(
            "$USERS_RESERVES_NODE/${user()?.uid ?: ""}/$foodKey" to foodMap,
            "$RESERVES_NODE/$foodKey" to foodMap
        )

        // Use updateChildren instead of setValue
        database.updateChildren(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    error(task.exception?.message ?: "Unknown error occurred")
                    onComplete(false)
                }
            }
    }

    fun orderFood(
        food: ReservedFood,
        onComplete: (Boolean) -> Unit,
        error: (String) -> Unit
    ) {
        // Generate a unique key for both paths
        val foodKey = usersFoodsRef.child(user()?.uid ?: "").push().key
            ?: run {
                error("Couldn't generate food ID")
                return
            }

        val randomCode = Random.nextInt(from = 0, 99999999)

        val foodMap = hashMapOf(
            "imgUrl" to food.imgUrl,
            "name" to food.name,
            "price" to food.price,
            "quantity" to food.quantity,
            "options" to food.options,
            "code" to randomCode.toString(),
            "address" to food.address,
            "phoneNumber" to food.phoneNumber,
            "documentId" to foodKey,
            "status" to "ordered",
            "lastStatus" to food.lastStatus,
            "reservedAt" to ServerValue.TIMESTAMP,
            "userId" to user()?.uid
        )

        // Create multi-location update paths
        val updates = hashMapOf<String, Any>(
            "$USERS_RESERVES_NODE/${user()?.uid ?: ""}/$foodKey" to foodMap,
            "$RESERVES_NODE/$foodKey" to foodMap
        )

        // Use updateChildren instead of setValue
        database.updateChildren(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    error(task.exception?.message ?: "Unknown error occurred")
                    onComplete(false)
                }
            }
    }

    fun getUserReserves(): Flow<Resources<List<Foods>>> = callbackFlow {

    }

    fun getFood(
        foodId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Foods?) -> Unit
    ) {
        foodsRef.child(foodId).get()
            .addOnSuccessListener { snapshot ->
                val food = snapshot.getValue(Foods::class.java)
                onSuccess(food?.copy(documentId = snapshot.key ?: ""))
            }
            .addOnFailureListener { onError(it) }
    }

    fun updateFood(
        id: Int,
        imgUrl: String,
        foodName: String,
        price: Int,
        availability: Boolean,
        amount: Int?,
        options: List<OptionState>?,
        documentId: String,
        onResult: (Boolean) -> Unit
    ) {
        val updates = hashMapOf<String, Any>(
            "id" to id,
            "imgUrl" to imgUrl,
            "foodName" to foodName,
            "price" to price,
            "availability" to availability,
            "amount" to (amount ?: 0),
            "options" to (options ?: emptyList<OptionState>())
        )

        foodsRef.child(documentId).updateChildren(updates)
            .addOnCompleteListener { onResult(it.isSuccessful) }
    }

    fun getReservedFoods(): Flow<Resources<List<ReservedFood>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val foods = if (snapshot.exists()) {
                    snapshot.children.mapNotNull { foodSnapshot ->
                        foodSnapshot.getValue(ReservedFood::class.java)?.copy(
                            documentId = foodSnapshot.key ?: ""
                        )
                    }.sortedByDescending { it.reservedAt }
                } else {
                    emptyList()
                }
                trySend(Resources.Success(foods))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resources.Error(error.toException()))
            }
        }

        reservesRef.addValueEventListener(listener)
        awaitClose { reservesRef.removeEventListener(listener) }
    }
}

sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)
}