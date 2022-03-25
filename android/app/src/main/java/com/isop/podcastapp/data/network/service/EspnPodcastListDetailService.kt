package com.isop.podcastapp.data.network.service

import com.isop.podcastapp.data.network.constant.PODCAST_DETAIL_LIST
import com.isop.podcastapp.data.network.model.podcastdetail.PodcastDetail
import retrofit2.http.GET
import retrofit2.http.Query

interface EspnPodcastListDetailService {

    @GET(PODCAST_DETAIL_LIST)
    suspend fun getPodcastsListDetail(@Query("showId") showId: String): PodcastDetail
}