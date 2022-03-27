package com.espn.podcastapp.domain.repository

import com.espn.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.espn.podcastapp.data.network.model.podcastlist.EspnPodcastList
import com.espn.podcastapp.error.Failure
import com.espn.podcastapp.util.Either

class PodcastRepositoryMockImpl : PodcastRepository {

    override suspend fun getPodcastsList(): Either<Failure, EspnPodcastList> {
        TODO("Not yet implemented")
    }

    override suspend fun getPodcastsListDetail(showId: String): Either<Failure, PodcastDetail> {
        TODO("Not yet implemented")
    }
}