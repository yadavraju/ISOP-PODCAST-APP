package com.isop.podcastapp.data.network.model.podcastdetail

import com.isop.podcastapp.data.network.model.podcastlist.Analytics

data class PodcastDetail(
    val analytics: Analytics,
    val content: Content,
    val productAPIURL: String,
    val resultsCount: Int,
    val resultsLimit: Int,
    val resultsOffset: Int,
    val status: String,
)