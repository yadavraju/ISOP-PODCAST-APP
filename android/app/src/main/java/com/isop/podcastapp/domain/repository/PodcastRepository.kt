package com.isop.podcastapp.domain.repository

import com.isop.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.isop.podcastapp.data.network.model.podcastlist.EspnPodcastList
import com.isop.podcastapp.domain.model.PodcastSearch
import com.isop.podcastapp.error.Failure
import com.isop.podcastapp.util.Either

interface PodcastRepository {

    suspend fun searchPodcasts(
        query: String,
        type: String,
    ): Either<Failure, PodcastSearch>

    suspend fun getPodcastsList(): Either<Failure, EspnPodcastList>

    suspend fun getPodcastsListDetail(showId: String): Either<Failure, PodcastDetail>
}