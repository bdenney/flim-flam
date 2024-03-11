package com.bdenney.modtwo.data.number

import javax.inject.Inject
import kotlin.random.Random

class LocalRandomNumberDataSource @Inject constructor(): RandomNumberDataSource {
    override suspend fun getRandomNumbers(number: Int, min: Int, max: Int): List<Int> {
        val numbers = mutableListOf<Int>()
        for (i in 0..<number) {
            numbers.add(Random.nextInt(min, max))
        }
        return numbers
    }
}