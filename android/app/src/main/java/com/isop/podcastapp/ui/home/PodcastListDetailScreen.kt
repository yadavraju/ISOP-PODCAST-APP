package com.isop.podcastapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.isop.podcastapp.data.network.model.podcastdetail.Item
import com.isop.podcastapp.ui.common.ViewModelProvider
import com.isop.podcastapp.ui.navigation.Destination
import com.isop.podcastapp.ui.navigation.Navigator
import com.isop.podcastapp.ui.podcast.PodcastImage
import com.isop.podcastapp.util.Resource

@Composable
fun PodcastListDetailScreen(
    podcastId: String,
) {
    val scrollState = rememberLazyListState()
    val navController = Navigator.current
    val podcastSearchViewModel = ViewModelProvider.podcastSearch
    val podcastListDetails = podcastSearchViewModel.podcastListDetail

    Surface {
        LazyColumn(state = scrollState) {
            when (podcastListDetails) {
                is Resource.Error -> {
                    item {
                        ErrorView(text = podcastListDetails.failure.translate()) {
                            podcastSearchViewModel.getPodcastsListDetail(podcastId)
                        }
                    }
                }
                Resource.Loading -> {
                    item {
                        LoadingPlaceholder()
                    }
                }
                is Resource.Success -> {
                    item {
                        PodcastImage(
                            url = podcastListDetails.data.content.background,
                            fitScaleType = false,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .statusBarsPadding()
                                .fillMaxWidth()
                                .height(240.dp)
                        )
                    }
                    item {
                        podcastListDetails.data.content.items.forEach { podcast ->
                            PodcastListDetailRowView(
                                podcast = podcastListDetails.data.content,
                                post = podcast
                            ) {
                                openPodcastDetail(navController, podcast)
                            }
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 32.dp)
                        .padding(bottom = if (ViewModelProvider.podcastPlayer.currentPlayingEpisode.value != null) 64.dp else 0.dp)
                )
            }
        }
    }
}

private fun openPodcastDetail(
    navController: NavHostController,
    podcast: Item,
) {
    navController.navigate(Destination.podcastDetail(podcast.id.toString())) { }
}