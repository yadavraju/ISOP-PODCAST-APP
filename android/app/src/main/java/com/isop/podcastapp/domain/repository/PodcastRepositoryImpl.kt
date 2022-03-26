package com.isop.podcastapp.domain.repository

import com.isop.podcastapp.data.datastore.PodcastDataStore
import com.isop.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.isop.podcastapp.data.network.model.podcastlist.EspnPodcastList
import com.isop.podcastapp.data.network.service.EspnPodcastListDetailService
import com.isop.podcastapp.data.network.service.EspnPodcastService
import com.isop.podcastapp.data.network.service.PodcastService
import com.isop.podcastapp.domain.model.PodcastSearch
import com.isop.podcastapp.error.Failure
import com.isop.podcastapp.util.Either
import com.isop.podcastapp.util.left
import com.isop.podcastapp.util.right

class PodcastRepositoryImpl(
    private val service: PodcastService,
    private val dataStore: PodcastDataStore,
    private val espnService: EspnPodcastService,
    private val espnListService: EspnPodcastListDetailService,
) : PodcastRepository {

    companion object {
        private const val TAG = "PodcastRepository"
    }

    override suspend fun searchPodcasts(
        query: String,
        type: String,
    ): Either<Failure, PodcastSearch> {
        return try {
            val canFetchAPI = dataStore.canFetchAPI()
            if (canFetchAPI) {
                val result = service.searchPodcasts(query, type).asDomainModel()
                dataStore.storePodcastSearchResult(result)
                right(result)
            } else {
                right(dataStore.readLastPodcastSearchResult())
            }
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun getPodcastsList(): Either<Failure, EspnPodcastList> {
        return try {
            val canFetchAPI = dataStore.canFetchAPI()
            if (!canFetchAPI) {
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
            val canFetchAPI = dataStore.canFetchAPI()
            if (!canFetchAPI) {
                val result = espnListService.getPodcastsListDetail(showId)
                dataStore.storePodcastListDetailResult(result)
                right(result)
            } else {
                right(dataStore.readLastPodcastListDetailResult())
            }
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }
}