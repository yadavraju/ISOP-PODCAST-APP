package com.espn.podcastapp.data.network.service

import com.espn.podcastapp.data.network.constant.PODCAST_DETAIL_LIST
import com.espn.podcastapp.data.network.model.podcastdetail.PodcastDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface EspnPodcastListDetailService {

    @GET(PODCAST_DETAIL_LIST)
    suspend fun getPodcastsListDetail(@Query("showId") showId: String): PodcastDetail
}