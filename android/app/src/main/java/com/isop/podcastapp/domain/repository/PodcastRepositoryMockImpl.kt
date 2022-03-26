package com.isop.podcastapp.domain.repository

import com.isop.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.isop.podcastapp.data.network.model.podcastlist.EspnPodcastList
import com.isop.podcastapp.error.Failure
import com.isop.podcastapp.util.Either

class PodcastRepositoryMockImpl : PodcastRepository {

    override suspend fun getPodcastsList(): Either<Failure, EspnPodcastList> {
        TODO("Not yet implemented")
    }

    override suspend fun getPodcastsListDetail(showId: String): Either<Failure, PodcastDetail> {
        TODO("Not yet implemented")
    }
}