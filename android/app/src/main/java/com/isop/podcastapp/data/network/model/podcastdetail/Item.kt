package com.isop.podcastapp.data.network.model.podcastdetail

data class Item(
    val action: String,
    val cellType: String,
    val description: String,
    val duration: Int,
    val headline: String,
    val id: String,
    val premium: Boolean,
    val published: String,
    val type: String,
    val url: String,
)