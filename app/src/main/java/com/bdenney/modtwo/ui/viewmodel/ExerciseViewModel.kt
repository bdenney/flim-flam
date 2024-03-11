package com.bdenney.modtwo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdenney.modtwo.data.number.RandomNumberRepository
import com.bdenney.modtwo.data.usermetrics.UserMetricRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ExerciseUiState {
    data object InProgress: ExerciseUiState()
    data object Complete: ExerciseUiState()
}

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val randomNumberRepository: RandomNumberRepository,
    private val userMetricRepository: UserMetricRepository
): ViewModel() {

    var uiState by mutableStateOf<ExerciseUiState>(ExerciseUiState.InProgress)
        private set

    var numbers by mutableStateOf<List<Int>>(listOf())
        private set

    var currentStep by mutableIntStateOf(0)
        private set

    var numberCorrect by mutableIntStateOf(0)
        private set

    init {
        viewModelScope.launch {
            numbers = randomNumberRepository.getRandomNumbers()
        }
    }

    fun onEvenClicked() {
        markAnswer(EvenOdd.EVEN)
    }

    fun onOddClicked() {
        markAnswer(EvenOdd.ODD)
    }

    private fun markAnswer(answer: EvenOdd) {
        // calculate the correct answer
        val nextNumber = numbers[currentStep] + 1

        // increment number correct if they got it right
        when (answer) {
            EvenOdd.EVEN -> if (nextNumber.isEven()) numberCorrect++
            EvenOdd.ODD -> if (nextNumber.isOdd()) numberCorrect++
        }

        // move to the next step
        currentStep += 1

        // check if we're done
        if (currentStep == numbers.size) {
            uiState = ExerciseUiState.Complete
            viewModelScope.launch {
                userMetricRepository.saveLastExerciseTime()
            }
        }
    }
}

private enum class EvenOdd {
    EVEN, ODD
}

private fun Int.isEven(): Boolean = this % 2 == 0
private fun Int.isOdd(): Boolean = !this.isEven()