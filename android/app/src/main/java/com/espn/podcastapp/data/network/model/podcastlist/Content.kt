package com.espn.podcastapp.data.network.model.podcastlist

data class Content(
    val cellType: String,
    val id: Int,
    val podcast: Podcast,
    val type: String,
)