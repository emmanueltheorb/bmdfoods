package com.orb.bmdfoods.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject

class NetworkMonitor @Inject constructor(
    private val application: Application
) {
    val isOnline = mutableStateOf(false)

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val networkCallback =
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isOnline.value = true
                }

                override fun onLost(network: Network) {
                    isOnline.value = false
                }
            }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}