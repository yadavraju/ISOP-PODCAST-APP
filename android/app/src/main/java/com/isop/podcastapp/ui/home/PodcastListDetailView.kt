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
import com.isop.podcastapp.data.network.model.podcastdetail.ContentDetails
import com.isop.podcastapp.data.network.model.podcastdetail.Item
import com.isop.podcastapp.data.network.model.podcastlist.Content
import com.isop.podcastapp.ui.podcast.PodcastImage

@Composable
fun PodcastListDetailView(
    podcast: ContentDetails,
    item: Item,
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
            url = if (isSystemInDarkTheme()) podcast.showLogoDark else podcast.showLogo,
            aspectRatio = 1f
        )
        Text(
            item.headline,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )
    }
}
