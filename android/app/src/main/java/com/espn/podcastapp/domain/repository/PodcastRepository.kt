package com.espn.podcastapp.domain.repository

import com.espn.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.espn.podcastapp.data.network.model.podcastlist.EspnPodcastList
import com.espn.podcastapp.error.Failure
import com.espn.podcastapp.util.Either

interface PodcastRepository {

    suspend fun getPodcastsList(): Either<Failure, EspnPodcastList>

    suspend fun getPodcastsListDetail(showId: String): Either<Failure, PodcastDetail>
}