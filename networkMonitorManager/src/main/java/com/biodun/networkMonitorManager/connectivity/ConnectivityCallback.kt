package com.biodun.networkMonitorManager.connectivity

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ConnectivityCallback : ConnectivityManager.NetworkCallback() {

    private val _networkStateLiveData = MutableLiveData<NetworkState>()
    val networkStateLiveData: LiveData<NetworkState> = _networkStateLiveData

    override fun onLost(network: Network) {
        super.onLost(network)
        _networkStateLiveData.postValue(NetworkState.Lost)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        _networkStateLiveData.postValue(NetworkState.UnAvailable)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        _networkStateLiveData.postValue(NetworkState.Available)
    }
}
