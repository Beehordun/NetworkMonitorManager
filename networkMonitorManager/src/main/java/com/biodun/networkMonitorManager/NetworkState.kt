package com.biodun.networkMonitorManager

sealed class NetworkState {
    object Available: NetworkState()
    object UnAvailable: NetworkState()
    object Lost: NetworkState()
}
