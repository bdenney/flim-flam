package com.bdenney.modtwo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdenney.modtwo.Config
import com.bdenney.modtwo.data.usermetrics.UserMetricRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

sealed class DashboardUiState {
    data object ExerciseAvailable: DashboardUiState()
    data object ExerciseUnavailable: DashboardUiState()
}
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userMetricRepository: UserMetricRepository
): ViewModel() {
    var uiState by mutableStateOf<DashboardUiState>(DashboardUiState.ExerciseUnavailable)
        private set

    var lastTime by mutableStateOf(LocalDateTime.now())
        private set

    init {
        viewModelScope.launch {
            userMetricRepository.getLastExerciseTime().collectLatest {
                lastTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.of("US/Eastern"))
                calculateState()
            }
        }
    }

    fun calculateState() {
        val allowedPeriod = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("US/Eastern")).minusMinutes(Config.MINUTE_DURATION_BETWEEN_ATTEMPTS)
        uiState = if (lastTime < allowedPeriod) {
            DashboardUiState.ExerciseAvailable
        } else {
            DashboardUiState.ExerciseUnavailable
        }
    }
}