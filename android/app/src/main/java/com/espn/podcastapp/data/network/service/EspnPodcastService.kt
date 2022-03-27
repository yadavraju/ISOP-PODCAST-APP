package com.espn.podcastapp.data.network.service

import com.espn.podcastapp.data.network.constant.PODCAST_ID_LIST
import com.espn.podcastapp.data.network.model.podcastlist.EspnPodcastList
import retrofit2.http.GET

interface EspnPodcastService {

    @GET(PODCAST_ID_LIST)
    suspend fun getPodcastsList(): EspnPodcastList
}