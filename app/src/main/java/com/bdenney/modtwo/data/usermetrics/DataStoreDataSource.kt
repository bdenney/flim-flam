package com.bdenney.modtwo.data.usermetrics

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreDataSource @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_metrics")

        private val LAST_EXERCISE_KEY = longPreferencesKey("last_exercise")
    }
    fun getLastExerciseDateTime(): Flow<Long> = appContext.dataStore.data.map { preferences ->
        preferences[LAST_EXERCISE_KEY] ?: 0L
    }

    suspend fun saveLastExerciseTime(time: Long) = withContext(Dispatchers.IO) {
        appContext.dataStore.edit { preferences ->
            System.currentTimeMillis()
            preferences[LAST_EXERCISE_KEY] = time
        }
    }
}