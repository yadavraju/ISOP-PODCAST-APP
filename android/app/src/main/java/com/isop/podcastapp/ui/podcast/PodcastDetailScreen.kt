package com.isop.podcastapp.ui.podcast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isop.podcastapp.R
import com.isop.podcastapp.ui.common.BackButton
import com.isop.podcastapp.ui.common.EmphasisText
import com.isop.podcastapp.ui.common.PrimaryButton
import com.isop.podcastapp.ui.common.ViewModelProvider
import com.isop.podcastapp.util.Resource
import com.isop.podcastapp.util.formatMillisecondsAsDate
import com.isop.podcastapp.util.toDurationMinutes
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun PodcastDetailScreen(
    podcastId: String,
) {
    val scrollState = rememberScrollState()
    val podcastSearchViewModel = ViewModelProvider.podcastSearch
    val detailViewModel = ViewModelProvider.podcastDetail
    val playerViewModel = ViewModelProvider.podcastPlayer
    val podcast = podcastSearchViewModel.getPodcastListDetail(podcastId)
    val contentDetails = podcastSearchViewModel.getPodcastListContentDetail()
    val currentContext = LocalContext.current

    Surface {
        Column(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            Row {
                BackButton()
            }

            if (podcast != null) {
                val playButtonText =
                    if (playerViewModel.podcastIsPlaying &&
                        playerViewModel.currentPlayingEpisode.value?.id == podcast.id
                    ) stringResource(R.string.pause) else stringResource(R.string.play)

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .navigationBarsPadding()
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                        .padding(bottom = if (playerViewModel.currentPlayingEpisode.value != null) 64.dp else 0.dp)

                ) {
                    contentDetails?.background?.let {
                        PodcastImage(
                            url = it,
                            modifier =  Modifier.fillMaxHeight(),
                            fitScaleType = false
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        podcast.headline,
                        style = MaterialTheme.typography.h1,
                        fontSize = 24.sp
                    )

                    Text(
                        "${podcast.type} : ${podcast.cellType}",
                        style = MaterialTheme.typography.body1
                    )

                    EmphasisText(
                        text = "${podcast.published.format("MMM dd")} • ${
                            podcast.duration.toLong().toDurationMinutes()
                        }"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row {
                        PrimaryButton(
                            text = playButtonText,
                            height = 48.dp
                        ) {
                            contentDetails?.items?.let {
                                playerViewModel.playPodcast(
                                    it,
                                    podcast
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        com.isop.podcastapp.ui.common.IconButton(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = stringResource(R.string.share)
                        ) {
                            detailViewModel.sharePodcastEpisode(currentContext, podcast)
                        }

                        com.isop.podcastapp.ui.common.IconButton(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = stringResource(R.string.source_web)
                        ) {
                            detailViewModel.openListenNotesURL(currentContext, podcast)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    EmphasisText(text = podcast.description)
                }
            }
        }
    }
}