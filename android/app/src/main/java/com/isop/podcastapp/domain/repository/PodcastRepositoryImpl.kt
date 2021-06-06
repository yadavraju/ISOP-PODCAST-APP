package com.isop.podcastapp.domain.repository

import com.isop.podcastapp.data.datastore.PodcastDataStore
import com.isop.podcastapp.data.network.service.PodcastService
import com.isop.podcastapp.domain.model.PodcastSearch
import com.isop.podcastapp.error.Failure
import com.isop.podcastapp.util.Either
import com.isop.podcastapp.util.left
import com.isop.podcastapp.util.right

class PodcastRepositoryImpl(
    private val service: PodcastService,
    private val dataStore: PodcastDataStore
) : PodcastRepository {

    companion object {
        private const val TAG = "PodcastRepository"
    }

    override suspend fun searchPodcasts(
        query: String,
        type: String
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
}