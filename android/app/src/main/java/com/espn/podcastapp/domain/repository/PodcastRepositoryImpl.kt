package com.espn.podcastapp.domain.repository

import androidx.datastore.preferences.core.longPreferencesKey
import com.espn.podcastapp.data.datastore.PodcastDataStore
import com.espn.podcastapp.data.datastore.PodcastDataStore.Companion.lastPodcastAPIFetchTime
import com.espn.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.espn.podcastapp.data.network.model.podcastlist.EspnPodcastList
import com.espn.podcastapp.data.network.service.EspnPodcastListDetailService
import com.espn.podcastapp.data.network.service.EspnPodcastService
import com.espn.podcastapp.error.Failure
import com.espn.podcastapp.util.Either
import com.espn.podcastapp.util.left
import com.espn.podcastapp.util.right

class PodcastRepositoryImpl(
    private val dataStore: PodcastDataStore,
    private val espnService: EspnPodcastService,
    private val espnListService: EspnPodcastListDetailService,
) : PodcastRepository {

    override suspend fun getPodcastsList(): Either<Failure, EspnPodcastList> {
        return try {
            val canFetchAPI = dataStore.canFetchAPI(lastPodcastAPIFetchTime)
            if (canFetchAPI) {
                val result = espnService.getPodcastsList()
                dataStore.storePodcastListResult(result)
                right(result)
            } else {
                right(dataStore.readLastPodcastListResult())
            }
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun getPodcastsListDetail(showId: String): Either<Failure, PodcastDetail> {
        return try {
            val canFetchAPI =
                dataStore.canFetchAPI(longPreferencesKey(PodcastDataStore.lastPodcastAPIFetchKey + showId))
            if (canFetchAPI) {
                val result = espnListService.getPodcastsListDetail(showId)
                dataStore.storePodcastListDetailResult(result)
                right(result)
            } else {
                right(dataStore.readLastPodcastListDetailResult(showId))
            }
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }
}