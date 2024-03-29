package com.app.freshworkstudio

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.app.freshworkstudio.utils.network.NetworkChangeReceiver
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class FreshWorkApp : Application() {

    companion object {
        lateinit var context: Context

        var networkChangeReceiver: NetworkChangeReceiver? = null

        val connectivityManager: ConnectivityManager by lazy {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        fun isInternetAvailable(): Boolean {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        fun registerEvent(){
            networkChangeReceiver?.registerNetworkEvent()
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        networkChangeReceiver = NetworkChangeReceiver()
    }




}
