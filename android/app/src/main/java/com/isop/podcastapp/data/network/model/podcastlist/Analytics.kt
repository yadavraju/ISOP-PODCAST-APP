package com.isop.podcastapp.data.network.model.podcastlist

import com.google.gson.annotations.SerializedName

data class Analytics(
    val league: String,
    val pageName: String,
    val sectionName: String,
    val sectionTitle: String,
    val sport: String,
    @SerializedName("Team")
    val team: String
)