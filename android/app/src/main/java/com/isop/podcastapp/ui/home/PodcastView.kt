package com.isop.podcastapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.isop.podcastapp.data.network.model.podcastlist.Content
import com.isop.podcastapp.domain.model.Episode
import com.isop.podcastapp.ui.podcast.PodcastImage

@Composable
fun PodcastView(
    podcast: Content,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.background)
            .clickable(onClick = onClick),
    ) {
        PodcastImage(
            url = if (isSystemInDarkTheme()) podcast.podcast.imageDark else podcast.podcast.image,
            aspectRatio = 1f
        )
        Text(
            podcast.podcast.headline,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )
    }
}
