package com.espn.podcastapp.data.network.model.podcastdetail
import com.google.gson.annotations.SerializedName

data class ContentDetails(
    val alerts: List<Alert>,
    val background: String,
    val id: String,
    val items: List<Item>,
    val premium: Boolean,
    val share: Share,
    val showLogo: String,
    @SerializedName("showLogo-dark")
    val showLogoDark: String,
    val showName: String,
    val updated: String,
)