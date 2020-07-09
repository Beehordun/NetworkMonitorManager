package com.biodun.networkMonitorManager.connectivity

sealed class NetworkState {
    object Available: NetworkState()
    object UnAvailable: NetworkState()
    object Lost: NetworkState()
}
