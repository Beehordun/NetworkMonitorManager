package com.biodun.networkMonitorManager

import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkRequestFactory {

    private val networkTypeMap: Map<NetworkType, Int> =
        hashMapOf(
            NetworkType.Wifi to NetworkCapabilities.TRANSPORT_WIFI,
            NetworkType.Mobile to NetworkCapabilities.TRANSPORT_CELLULAR
        )

    fun getNetworkRequest(networkTypeList: List<NetworkType> = emptyList()): NetworkRequest {
        val networkRequestBuilder: NetworkRequest.Builder = NetworkRequest.Builder()

        if (networkTypeList.isNotEmpty()) {
            for (networkType in networkTypeList) {
                networkRequestBuilder.addTransportType(networkTypeMap.getValue(networkType))
            }
        }
        networkRequestBuilder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        networkRequestBuilder.addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)

        return networkRequestBuilder.build()
    }
}
