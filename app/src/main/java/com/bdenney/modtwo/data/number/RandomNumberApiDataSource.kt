package com.bdenney.modtwo.data.number

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private interface RandomService {
    @GET("integers/?col=1&base=10&format=plain&rnd=new")
    suspend fun getRandomNumbers(
        @Query("num") number: Int,
        @Query("min") min: Int,
        @Query("max") max: Int,
    ): List<Int>
}
class RandomNumberApiDataSource @Inject constructor(): RandomNumberDataSource, RandomService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.random.org/")
        .addConverterFactory(IntListConverterFactory())
        .build()

    private val client = retrofit.create(RandomService::class.java)

    override suspend fun getRandomNumbers(number: Int, min: Int, max: Int): List<Int> {
        return client.getRandomNumbers(number, min, max)
    }

}
