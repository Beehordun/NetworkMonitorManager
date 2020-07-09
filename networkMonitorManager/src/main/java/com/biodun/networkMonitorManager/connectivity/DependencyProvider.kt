package com.biodun.networkMonitorManager.connectivity

object DependencyProvider {
    fun provideConnectivityCallback() = ConnectivityCallback()
    fun provideNetworkRequestFactory() = NetworkRequestFactory()
}
