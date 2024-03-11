package com.bdenney.modtwo.data.number

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RandomNumberModule {
    @Binds
    abstract fun bindRandomNumberDataSource(
        randomNumberApiDataSource: RandomNumberApiDataSource
    ): RandomNumberDataSource
}