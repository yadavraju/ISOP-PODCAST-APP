package com.isop.podcastapp.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.isop.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.isop.podcastapp.data.network.model.podcastlist.EspnPodcastList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant

class PodcastDataStore(
    private val context: Context,
) {

    suspend fun storePodcastListResult(data: EspnPodcastList) {
        context.podcastDataStore.edit { preferences ->
            val jsonString = Gson().toJson(data)
            Log.i(TAG, jsonString)
            preferences[lastPodcastAPIFetchTime] = Instant.now().toEpochMilli()
            preferences[podcastLisResult] = jsonString
        }
    }

    suspend fun readLastPodcastListResult(): EspnPodcastList {
        return context.podcastDataStore.data.map { preferences ->
            val jsonString = preferences[podcastLisResult]
            Gson().fromJson(jsonString, EspnPodcastList::class.java)
        }.first()
    }

    suspend fun storePodcastListDetailResult(data: PodcastDetail) {
        context.podcastDataStore.edit { preferences ->
            val jsonString = Gson().toJson(data)
            Log.i(TAG, jsonString)
            preferences[longPreferencesKey(lastPodcastAPIFetchKey + data.content.id)] =
                Instant.now().toEpochMilli()
            preferences[stringPreferencesKey(podcastListDetailKey + data.content.id)] =
                jsonString
        }
    }

    suspend fun readLastPodcastListDetailResult(id: String): PodcastDetail {
        return context.podcastDataStore.data.map { preferences ->
            val jsonString = preferences[stringPreferencesKey(podcastListDetailKey + id)]
            Gson().fromJson(jsonString, PodcastDetail::class.java)
        }.first()
    }

    suspend fun canFetchAPI(key: Preferences.Key<Long>): Boolean {
        return context.podcastDataStore.data.map { preferences ->
            val epochMillis = preferences[key]

            return@map if (epochMillis != null) {
                val minDiffMillis = 36 * 60 * 60 * 1000L
                val now = Instant.now().toEpochMilli()
                (now - minDiffMillis) > epochMillis
            } else {
                true
            }
        }.first()
    }

    companion object {
        private const val TAG = "PodcastDataStore"
        const val lastPodcastAPIFetchKey = "lastPodcastAPIFetchKey"
        const val podcastListDetailKey = "podcastListDetailResult"
        private val podcastLisResult = stringPreferencesKey("podcastLisResult")
        val lastPodcastAPIFetchTime = longPreferencesKey("lastPodcastAPIFetchTime")
    }
}

private val Context.podcastDataStore: DataStore<Preferences> by preferencesDataStore(name = "podcasts")
