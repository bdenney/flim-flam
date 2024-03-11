package com.bdenney.modtwo.data.number

interface RandomNumberDataSource {
    suspend fun getRandomNumbers(number: Int, min: Int, max: Int): List<Int>
}