package com.apsl.glideapp.app

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.compose.rememberNavController
import com.apsl.glideapp.core.ui.None
import com.apsl.glideapp.core.ui.theme.GlideAppTheme
import com.apsl.glideapp.core.util.android.LoggingLifecycleObserver
import com.apsl.glideapp.core.util.android.addObservers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpEdgeToEdge()
        super.onCreate(savedInstanceState)
        init()
        setContent {
            GlideAppTheme {
                val navHostController = rememberNavController()
                Scaffold(
                    contentWindowInsets = WindowInsets.None,
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

    private fun init() {
        registerLifecycleObservers()
        requestNotificationPermission()
        viewModel.updateAppConfiguration()
    }

    private fun registerLifecycleObservers() {
        val observers = mutableListOf<LifecycleObserver>()
        if (BuildConfig.DEBUG) {
            observers.add(LoggingLifecycleObserver)
        }
        lifecycle.addObservers(observers)
    }

    private fun requestNotificationPermission() {
        //TODO: Handle for Android 13 and higher
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val areNotificationsEnabled = notificationManager.areNotificationsEnabled()

        if (!areNotificationsEnabled) {
            val requestPermissionLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {}
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
