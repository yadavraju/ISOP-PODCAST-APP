package com.isop.podcastapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.isop.podcastapp.data.network.model.podcastdetail.ContentDetails
import com.isop.podcastapp.data.network.model.podcastdetail.Item
import com.isop.podcastapp.ui.podcast.PodcastImage
import com.isop.podcastapp.util.toDurationMinutes

@Composable
fun PodcastListDetailRowView(
    podcast: ContentDetails,
    item: Item,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        PostImage(podcast, Modifier.padding(end = 16.dp))
        Column(modifier = Modifier.weight(1f)) {
            PostTitle(item)
            AuthorAndReadTime(item)
        }
    }
}

@Composable
fun AuthorAndReadTime(
    item: Item,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = item.type + " " + item.duration.toLong().toDurationMinutes(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun PostImage(details: ContentDetails, modifier: Modifier = Modifier) {
    PodcastImage(
        url = if (isSystemInDarkTheme()) details.showLogoDark else details.showLogo,
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun PostTitle(item: Item) {
    Text(item.headline, style = MaterialTheme.typography.subtitle1)
}
