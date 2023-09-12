package com.apsl.glideapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.compose.rememberNavController
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.LoggingLifecycleObserver
import com.apsl.glideapp.core.util.addObservers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContent {
            GlideAppTheme {
                val navHostController = rememberNavController()
                Navigation(navHostController)
            }
        }
    }

    private fun init() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        registerLifecycleObservers()
        viewModel.updateAppConfiguration()
    }

    private fun registerLifecycleObservers() {
        val observers = mutableListOf<LifecycleObserver>()
        if (BuildConfig.DEBUG) {
            observers.add(LoggingLifecycleObserver)
        }
        lifecycle.addObservers(observers)
    }
}
