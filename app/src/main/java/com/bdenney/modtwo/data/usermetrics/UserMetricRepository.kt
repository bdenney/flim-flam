package com.bdenney.modtwo.data.usermetrics

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserMetricRepository @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
) {
    fun getLastExerciseTime(): Flow<Long> {
        return dataStoreDataSource.getLastExerciseDateTime()
    }

    suspend fun saveLastExerciseTime() {
        dataStoreDataSource.saveLastExerciseTime(System.currentTimeMillis())
    }
}