package com.espn.podcastapp.data.network.model.podcastdetail

import com.espn.podcastapp.data.network.model.podcastlist.Analytics

data class PodcastDetail(
    val analytics: Analytics,
    val content: ContentDetails,
    val productAPIURL: String,
    val resultsCount: Int,
    val resultsLimit: Int,
    val resultsOffset: Int,
    val status: String,
)