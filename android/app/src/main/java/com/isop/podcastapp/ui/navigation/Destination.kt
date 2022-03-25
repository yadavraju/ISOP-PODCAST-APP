package com.isop.podcastapp.ui.navigation

object Destination {
    const val welcome = "welcome"
    const val home = "home"
    const val podcast = "podcast/{id}"
    const val podcastDetail = "podcastList/{id}"

    fun podcast(id: String): String = "podcast/$id"
    fun podcastDetail(id: String): String = "podcastList/$id"
}