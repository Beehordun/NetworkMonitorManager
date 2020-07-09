package com.biodun.netwatcher

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.biodun.networkMonitorManager.NetworkMonitorManager
import com.biodun.networkMonitorManager.NetworkState
import com.biodun.networkMonitorManager.NetworkType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val owner = { lifecycle }

    private val networkMonitorManager: NetworkMonitorManager by lazy {
        NetworkMonitorManager.getInstance(this, listOf(NetworkType.Wifi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        networkMonitorManager.registerCallback()
        //observeNetworkStateLiveData()
        collectNetworkStateFlow()
    }

    private fun observeNetworkStateLiveData() {
        networkMonitorManager.networkState.observe(owner) { network ->
            when (network) {
                is NetworkState.Available -> Toast.makeText(
                    this@MainActivity,
                    "Available",
                    Toast.LENGTH_LONG
                ).show()
                is NetworkState.UnAvailable -> Toast.makeText(
                    this@MainActivity,
                    "UnAvailable",
                    Toast.LENGTH_LONG
                ).show()
                is NetworkState.Lost -> Toast.makeText(this@MainActivity, "Lost", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun collectNetworkStateFlow() {
        networkMonitorManager.networkStateScope.launch {
            networkMonitorManager.networkStateFlow.collect { networkState ->
                when (networkState) {
                    is NetworkState.Available -> {
                        Toast.makeText(this@MainActivity, "Flow Available", Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkState.UnAvailable -> {
                        Toast.makeText(this@MainActivity, " Flow UnAvailable", Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkState.Lost -> {
                        Toast.makeText(this@MainActivity, "Flow Lost", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        networkMonitorManager.unRegisterCallback()
    }
}
