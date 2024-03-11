package com.bdenney.modtwo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bdenney.modtwo.R
import com.bdenney.modtwo.ui.viewmodel.DashboardUiState
import com.bdenney.modtwo.ui.viewmodel.DashboardViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(
    navController: NavController,
    dashboardViewModel: DashboardViewModel
) {
    ExerciseAvailableView(dashboardViewModel.lastTime, dashboardViewModel.uiState) {
        navController.navigate("exercise")
    }
}

@Composable
fun ExerciseAvailableView(lastTime: LocalDateTime, uiState: DashboardUiState, onExercise: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        Color.Transparent
                    )
                )
            )
            .padding(start = 16.dp, end = 16.dp)
    ) {



        when (uiState) {
            is DashboardUiState.ExerciseAvailable -> {
                Button(
                    onClick = { onExercise() },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Let's go!",
                        fontSize = 24.sp
                    )
                }
            }
            is DashboardUiState.ExerciseUnavailable -> {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .width(96.dp)
                        .height(96.dp)
                )
                Text(
                    text = "You can try again later!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }


    }
}
@Preview(showBackground = true)
@Composable
fun PreviewExerciseAvailableView() {
    ExerciseAvailableView(LocalDateTime.now(), DashboardUiState.ExerciseAvailable, {})
}

@Preview(showBackground = true)
@Composable
fun PreviewExerciseUnavailableView() {
    ExerciseAvailableView(LocalDateTime.now(), DashboardUiState.ExerciseUnavailable, {})
}