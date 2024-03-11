package com.bdenney.modtwo.data.number

import com.bdenney.modtwo.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomNumberRepository @Inject constructor(
    private val randomNumberDataSource: RandomNumberDataSource
) {
    suspend fun getRandomNumbers(): List<Int> {
        return withContext(Dispatchers.IO) {
            randomNumberDataSource.getRandomNumbers(
                number = Config.NUM_IN_SERIES,
                max = Config.MAX_NUM_RANGE,
                min = Config.MIN_NUM_RANGE
            )
        }
    }
}