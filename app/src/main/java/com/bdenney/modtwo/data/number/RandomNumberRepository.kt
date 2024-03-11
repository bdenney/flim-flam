package com.bdenney.modtwo.data.number

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomNumberRepository @Inject constructor(
    private val randomNumberDataSource: RandomNumberDataSource
) {
    suspend fun getRandomNumbers(): List<Int> {
        return withContext(Dispatchers.IO) {
            // TODO: Pull from configuration
            randomNumberDataSource.getRandomNumbers(
                number = 10,
                max = 100,
                min = 1
            )
        }
    }
}