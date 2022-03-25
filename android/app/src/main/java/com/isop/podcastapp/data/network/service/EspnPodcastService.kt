package com.isop.podcastapp.data.network.service

import com.isop.podcastapp.data.network.constant.PODCAST_ID_LIST
import com.isop.podcastapp.data.network.model.PodcastSearchDto
import com.isop.podcastapp.data.network.model.podcastlist.EspnPodcastListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EspnPodcastService {

    @GET(PODCAST_ID_LIST)
    suspend fun getPodcastsList(): EspnPodcastListDto
}