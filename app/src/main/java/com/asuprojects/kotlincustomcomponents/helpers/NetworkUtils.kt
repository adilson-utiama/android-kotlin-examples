package com.asuprojects.kotlincustomcomponents.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class NetworkUtils {

    companion object {

        fun addNetworkCallback(activity: Activity): ConnectivityManager.NetworkCallback? {
            val connectivityManager =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val connectivityManagerCallback: ConnectivityManager.NetworkCallback =
                    object : ConnectivityManager.NetworkCallback() {
                        private val activeNetworks: MutableList<Network> = mutableListOf()

                        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                        override fun onAvailable(network: Network) {
                            super.onAvailable(network)
                            // Add to list of active networks if not already in list
                            if (activeNetworks.none { activeNetwork -> activeNetwork.networkHandle == network.networkHandle }) activeNetworks.add(
                                network
                            )
                            val isNetworkConnected = activeNetworks.isNotEmpty()

                            ToastUtil.msg(activity, "Connection Available!")
                        }

                        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                        override fun onLost(network: Network) {
                            super.onLost(network)

                            // Remove network from active network list
                            activeNetworks.removeAll { activeNetwork -> activeNetwork.networkHandle == network.networkHandle }
                            val isNetworkConnected = activeNetworks.isNotEmpty()

                            ToastUtil.msg(activity, "Connection Lost...")
                        }
                    }

                // When registering a network callback using registerNetworkCallback instead of registerDefaultNetworkCallback, the callback is called for
                // every separate network instead of the default network. For example, if both WiFi and Cellular Data are connected, and only Cellular Data
                // is turned off, onLost() will be called. This does not mean that the device network is lost, but rather that the specific network has been
                // lost. The device could still be connected via another network. Because of this, a list of activeNetworks is maintained, and used to check
                // if the device is connected to any active networks.
                val builder = NetworkRequest.Builder()
                builder.addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                connectivityManager.registerNetworkCallback(builder.build(), connectivityManagerCallback)

                return connectivityManagerCallback
            }else{
                ToastUtil.msg(activity, "Use isConnected(), API lower than ${Build.VERSION_CODES.O}")

                return null
            }

        }

        /**
         * Observação: o getActiveNetworkInfo() teve o uso suspenso no Android 10.
         * Use NetworkCallbacks para apps que segmentam o Android 10 (API de nível 29) e versões posteriores.
         */

        fun isConected(activity: Activity): Boolean {
            val conmag =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = conmag.activeNetworkInfo
            val connected = activeNetwork?.isConnected == true
            Log.i("TESTE", "Conexao Internet Ativa: $connected")

            return activeNetwork?.isConnected == true
        }

        private fun hasNetworkAvailable(context: Context): Boolean {
            val service = Context.CONNECTIVITY_SERVICE
            val manager = context.getSystemService(service) as ConnectivityManager?
            val network = manager?.activeNetworkInfo
            Log.i("TESTE", "hasNetworkAvailable: ${(network != null)}")
            return (network?.isConnected) ?: false
        }

        fun hasInternetConnected(context: Context, url: String): Boolean {
            if (hasNetworkAvailable(context)) {
                try {
                    val connection = URL(url).openConnection() as HttpURLConnection
                    connection.setRequestProperty("User-Agent", "Test")
                    connection.setRequestProperty("Connection", "close")
                    connection.connectTimeout = 1500 // configurable
                    connection.connect()
                    Log.i("TESTE", "hasInternetConnected: ${(connection.responseCode == 200)}")
                    return (connection.responseCode == 200)
                } catch (e: IOException) {
                    Log.i("TESTE", "Error checking internet connection", e)
                }
            } else {
                Log.i("TESTE", "No network available!")
            }
            Log.i("TESTE", "hasInternetConnected: false")
            return false
        }

        fun hasServerConnected(context: Context, serverUrl: String): Boolean {
            if (hasNetworkAvailable(context)) {
                try {
                    val connection = URL(serverUrl).openConnection() as HttpURLConnection
                    connection.setRequestProperty("User-Agent", "Test")
                    connection.setRequestProperty("Connection", "close")
                    connection.connectTimeout = 1500 // configurable
                    connection.connect()
                    Log.i("TESTE", "hasServerConnected: ${(connection.responseCode == 200)}")
                    return (connection.responseCode == 200)
                } catch (e: IOException) {
                    Log.i("TESTE", "Error checking internet connection", e)
                }
            } else {
                Log.i("TESTE", "Server is unavailable!")
            }
            Log.i("TESTE", "hasServerConnected: false")
            return false
        }
    }
}