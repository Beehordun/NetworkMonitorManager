package com.biodun.networkMonitorManager

object DependencyProvider {
    fun provideConnectivityCallback() =
        ConnectivityCallback()
    fun provideNetworkRequestFactory() =
        NetworkRequestFactory()
}
