package com.bdenney.modtwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bdenney.modtwo.Config.Companion.MINUTE_REMINDER_PERIOD
import com.bdenney.modtwo.ui.screen.DashboardScreen
import com.bdenney.modtwo.ui.screen.ExerciseScreen
import com.bdenney.modtwo.ui.theme.TemplateTheme
import com.bdenney.modtwo.ui.viewmodel.DashboardViewModel
import com.bdenney.modtwo.ui.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemplateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard"
                    ) {
                        composable("exercise") {
                            val exerciseViewModel = hiltViewModel<ExerciseViewModel>()
                            ExerciseScreen(
                                navController = navController,
                                exerciseViewModel = exerciseViewModel
                            )
                        }
                        composable("dashboard") {
                            val dashboardViewModel = hiltViewModel<DashboardViewModel>()
                            DashboardScreen(
                                navController = navController,
                                dashboardViewModel = dashboardViewModel
                            )
                        }
                    }

                }
            }
        }

        manageReminders()
    }

    private fun manageReminders() {
        val reminderWork = PeriodicWorkRequestBuilder<NotificationWorker>(
            MINUTE_REMINDER_PERIOD,
            TimeUnit.MINUTES
        )
            .setInitialDelay(MINUTE_REMINDER_PERIOD, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            WORKER_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            reminderWork
        )
    }
}