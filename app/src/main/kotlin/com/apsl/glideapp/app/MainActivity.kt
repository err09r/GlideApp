package com.apsl.glideapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.apsl.glideapp.core.ui.none
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.LoggingLifecycleObserver
import com.apsl.glideapp.core.util.android.addObservers
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private var shouldShowSplashScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpEdgeToEdge()
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setUp() // Subscription on viewmodel's flow must happen after super.onCreate()
        init()

        setContent {
            GlideAppTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    contentWindowInsets = WindowInsets.none,
                    containerColor = MaterialTheme.colorScheme.background
                ) { padding ->
                    Navigation(
                        navHostController = navHostController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                }
            }
        }
    }

    private fun setUpEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun SplashScreen.setUp() {
        this.setKeepOnScreenCondition { shouldShowSplashScreen }

        viewModel.uiState
            .onEach { shouldShowSplashScreen = it.isSyncing }
            .launchIn(lifecycleScope)
    }

    private fun init() {
        registerLifecycleObservers()
        viewModel.sync()
    }

    private fun registerLifecycleObservers() {
        val observers = mutableListOf<LifecycleObserver>()
        if (BuildConfig.DEBUG) {
            observers.add(LoggingLifecycleObserver)
        }
        lifecycle.addObservers(observers)
    }
}
