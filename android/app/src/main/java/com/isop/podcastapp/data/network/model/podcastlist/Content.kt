package com.isop.podcastapp.data.network.model.podcastlist

data class Content(
    val cellType: String,
    val id: Int,
    val podcast: Podcast,
    val type: String,
)