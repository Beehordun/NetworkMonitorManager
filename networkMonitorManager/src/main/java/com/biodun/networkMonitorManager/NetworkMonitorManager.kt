package com.biodun.networkMonitorManager

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.asFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow

private const val REQUEST_TIME_OUT_MS = 1000

class NetworkMonitorManager(
    context: Context,
    private val callback: ConnectivityCallback,
    private val networkRequestFactory: NetworkRequestFactory
) {
    private val connectivityManager: ConnectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkState
        get() = callback.networkStateLiveData

    val networkStateFlow: Flow<NetworkState>
        get() = networkState.asFlow()

    lateinit var networkStateScope: CoroutineScope

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerCallback() {
        val networkRequest: NetworkRequest =
            networkRequestFactory.getNetworkRequest(mNetworkListType)

        connectivityManager.apply {
            registerNetworkCallback(networkRequest, callback)
            if (connectivityManager.allNetworks.isEmpty()) {
                requestNetwork(networkRequest, callback,
                    REQUEST_TIME_OUT_MS
                )
            }
        }

        networkStateScope = CoroutineScope(
            Job() +
                    Dispatchers.Main +
                    CoroutineName("Network State")
        )
    }

    fun unRegisterCallback() {
        connectivityManager.unregisterNetworkCallback(callback)
        networkStateScope.cancel()
    }

    companion object {
        var networkMonitorManager: NetworkMonitorManager? = null
        private var mNetworkListType: List<NetworkType> = emptyList()

        fun getInstance(
            context: Context,
            networkTypeList: List<NetworkType> = emptyList()
        ): NetworkMonitorManager {
            mNetworkListType = networkTypeList

            if (networkMonitorManager == null) {
                synchronized(this) {
                    networkMonitorManager =
                        NetworkMonitorManager(
                            context,
                            DependencyProvider.provideConnectivityCallback(),
                            DependencyProvider.provideNetworkRequestFactory()
                        )
                }
            }
            return networkMonitorManager as NetworkMonitorManager
        }
    }
}
