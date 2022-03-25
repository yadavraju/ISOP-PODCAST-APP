package com.isop.podcastapp.data.network.model.podcastlist


import com.google.gson.annotations.SerializedName

data class Podcast(
    @SerializedName("action")
    val action: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("headline")
    val headline: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("image-dark")
    val imageDark: String
)