package com.isop.podcastapp.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.isop.podcastapp.R
import com.isop.podcastapp.data.network.model.podcastdetail.Item
import com.isop.podcastapp.domain.model.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PodcastDetailViewModel @Inject constructor() : ViewModel() {

    fun sharePodcastEpisode(context: Context, episode: Item) {
        val text = context.getString(
            R.string.share_podcast_content,
            episode.headline,
            episode.url
        )
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TITLE, episode.headline)
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    fun openListenNotesURL(context: Context, episode: Item) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(episode.url))
        context.startActivity(webIntent)
    }
}