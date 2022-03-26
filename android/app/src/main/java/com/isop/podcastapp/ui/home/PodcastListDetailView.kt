package com.isop.podcastapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import com.isop.podcastapp.data.network.model.podcastdetail.ContentDetails
import com.isop.podcastapp.data.network.model.podcastdetail.Item
import com.isop.podcastapp.ui.podcast.PodcastImage
import com.isop.podcastapp.util.toDurationMinutes

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
        )
        Text(
            item.headline,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun PodcastListDetailRowView(
    podcast: ContentDetails,
    item: Item,
    onClick: () -> Unit,
) {
    MusicCardSimple(item, podcast, onClick = onClick, onToggleFavorite = {})
}

@Composable
fun MusicCardSimple(
    post: Item,
    podcast: ContentDetails,
    onClick: () -> Unit,
    isPlay: Boolean = false,
    onToggleFavorite: () -> Unit,
) {
    val bookmarkAction = if (isPlay) "Play" else "Pause"
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
            .semantics {
                // By defining a custom action, we tell accessibility services that this whole
                // composable has an action attached to it. The accessibility service can choose
                // how to best communicate this action to the user.
                customActions = listOf(
                    CustomAccessibilityAction(
                        label = bookmarkAction,
                        action = { onToggleFavorite(); true }
                    )
                )
            }
    ) {
        PostImage(podcast, Modifier.padding(end = 16.dp))
        Column(modifier = Modifier.weight(1f)) {
            PostTitle(post)
            AuthorAndReadTime(post)
        }
        BookmarkButton(
            isPay = isPlay,
            onClick = onToggleFavorite,
            // Remove button semantics so action can be handled at row level
            modifier = Modifier.clearAndSetSemantics {},
            contentAlpha = ContentAlpha.medium
        )
    }
}

@Composable
fun AuthorAndReadTime(
    post: Item,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = post.type + " " + post.duration.toLong().toDurationMinutes(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun PostImage(podcast: ContentDetails, modifier: Modifier = Modifier) {
    PodcastImage(
        url = if (isSystemInDarkTheme()) podcast.showLogoDark else podcast.showLogo,
        modifier = modifier
            .size(40.dp, 40.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun PostTitle(post: Item) {
    Text(post.headline, style = MaterialTheme.typography.subtitle1)
}

@Composable
fun BookmarkButton(
    isPay: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentAlpha: Float = ContentAlpha.high,
) {
    val clickLabel = if (isPay) "Play" else "Pause"

    CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
        IconToggleButton(
            checked = isPay,
            onCheckedChange = { onClick() },
            modifier = modifier.semantics {
                // Use a custom click label that accessibility services can communicate to the user.
                // We only want to override the label, not the actual action, so for the action we pass null.
                this.onClick(label = clickLabel, action = null)
            }
        ) {
            Icon(
                modifier = Modifier.padding(bottom = 16.dp),
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null
            )
        }
    }
}
