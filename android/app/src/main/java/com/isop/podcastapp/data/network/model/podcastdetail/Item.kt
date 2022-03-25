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
    val image: String = "https://artwork.espncdn.com/categories/54c9bf63-2027-4737-913c-0fe02e5a00c5/16x9-background/1296x729_20210903182307.jpg",
)