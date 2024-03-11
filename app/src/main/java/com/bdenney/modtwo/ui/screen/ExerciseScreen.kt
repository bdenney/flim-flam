package com.bdenney.modtwo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bdenney.modtwo.ui.viewmodel.ExerciseUiState
import com.bdenney.modtwo.ui.viewmodel.ExerciseViewModel

@Composable
fun ExerciseScreen(
    navController: NavController,
    exerciseViewModel: ExerciseViewModel
) {
    val step = exerciseViewModel.currentStep.toFloat()
    val numbers = exerciseViewModel.numbers
    val progress = if (numbers.isNotEmpty()) (step / numbers.size) else 0f

    when (exerciseViewModel.uiState) {
        is ExerciseUiState.InProgress -> EntryArea(
            progress = progress,
            number = if (numbers.isNotEmpty()) numbers[step.toInt()] else 0,
            onEvenClick = { exerciseViewModel.onEvenClicked() },
            onOddClick = { exerciseViewModel.onOddClicked() }
        )
        is ExerciseUiState.Complete -> {
            val percentCorrect = exerciseViewModel.numberCorrect / numbers.size.toFloat()
            CompleteView(percentCorrect) {
                navController.navigate("dashboard")
            }
        }
    }
}

@Composable
fun CompleteView(percentCorrect: Float, onComplete: () -> Unit) {
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
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Great job! You got ${(percentCorrect * 100).toInt()}% correct",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onComplete() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Finish",
                fontSize = 36.sp,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCompleteView() {
    CompleteView(.58f, {})
}

@Composable
fun EntryArea(progress: Float, number: Int, onEvenClick: () -> Unit, onOddClick: () -> Unit) {
    Box(
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
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .padding(start = 32.dp, end = 32.dp)
                .align(Alignment.TopCenter)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Text(
                text = "Guess what's next",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = number.toString(),
                fontSize = 256.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Button(
                onClick = onEvenClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Even",
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onOddClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Odd",
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EntryAreaPreview() {
    EntryArea(
        progress = .5f,
        number = 42,
        {},
        {}
    )
}