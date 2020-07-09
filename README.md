# NetworkMonitorManager

An Android library for observing network states as either Livedata or Flow.

# Installation

**Gradle:**

` implementation 'com.biodun.networkMonitorManager:networkMonitorManager:0.0.1' `

**Maven:**

```
<dependency>
  <groupId>com.biodun.networkMonitorManager</groupId>
  <artifactId>networkMonitorManager</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```
# Usage

1. **Get an instance of the NetworkMonitorManager:**

```
 private val networkMonitorManager: NetworkMonitorManager by lazy {
        NetworkMonitorManager.getInstance(this)
 }
```
 This gets an instance of NetworkMonitorManager that listens for both WIFI and CELLULAR networks. 
To specify specific network(s), get an instance of NetworkMonitorManager this way:

```
 private val networkMonitorManager: NetworkMonitorManager by lazy {
        NetworkMonitorManager.getInstance(this, listOf(NetworkType.Wifi))
 }
```

2. **Register and Unregister the NetworkMonitorManager:**

```
 @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        networkMonitorManager.registerCallback()
    }
```

```
 override fun onStop() {
        super.onStop()
        networkMonitorManager.unRegisterCallback()
 }
 
 ```
 
 3. **Observe the network:**
    
    **Flow:**
```
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        ...
        collectNetworkStateFlow() 
    }
```
```
  private fun collectNetworkStateFlow() {
        networkMonitorManager.networkStateScope.launch {
            networkMonitorManager.networkStateFlow.collect { networkState ->
                when (networkState) {
                    is NetworkState.Available -> {
                        Toast.makeText(this@MainActivity, "Flow Available", Toast.LENGTH_LONG).show()
                    }
                    is NetworkState.UnAvailable -> {
                        Toast.makeText(this@MainActivity, " Flow UnAvailable", Toast.LENGTH_LONG).show()
                    }
                    is NetworkState.Lost -> {
                        Toast.makeText(this@MainActivity, "Flow Lost", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

```

**LiveData:**

```
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        ...
        observeNetworkStateLiveData()
    }
```

```
 private fun observeNetworkStateLiveData() {
        networkMonitorManager.networkState.observe(owner) { network ->
            when (network) {
                is NetworkState.Available -> Toast.makeText(this@MainActivity, "Available",  Toast.LENGTH_LONG).show()
                is NetworkState.UnAvailable -> Toast.makeText(this@MainActivity, "UnAvailable",  Toast.LENGTH_LONG).show()
                is NetworkState.Lost -> Toast.makeText(this@MainActivity, "Lost", Toast.LENGTH_LONG).show()
            }
        }
 }
```



