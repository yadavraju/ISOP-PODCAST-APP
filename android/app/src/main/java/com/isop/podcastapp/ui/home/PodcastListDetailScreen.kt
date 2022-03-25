package com.isop.podcastapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsPadding
import com.isop.podcastapp.data.network.model.podcastlist.Content
import com.isop.podcastapp.ui.common.PreviewContent
import com.isop.podcastapp.ui.common.StaggeredVerticalGrid
import com.isop.podcastapp.ui.common.ViewModelProvider
import com.isop.podcastapp.ui.navigation.Destination
import com.isop.podcastapp.ui.navigation.Navigator
import com.isop.podcastapp.util.Resource

@Composable
fun PodcastListDetailScreen(
    podcastId: String,
) {
    val scrollState = rememberLazyListState()
    val navController = Navigator.current
    val podcastSearchViewModel = ViewModelProvider.podcastSearch
    val podcastList = podcastSearchViewModel.podcastList

    Surface {
        LazyColumn(state = scrollState) {
            item {
                LargeTitle()
            }

            when (podcastList) {
                is Resource.Error -> {
                    item {
                        ErrorView(text = podcastList.failure.translate()) {
                            podcastSearchViewModel.getPodcastsList()
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
                        StaggeredVerticalGrid(
                            crossAxisCount = 2,
                            spacing = 16.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            podcastList.data.content.forEach { podcast ->
                                PodcastView(
                                    podcast = podcast,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                ) {
                                    openPodcastDetail(navController, podcast)
                                }
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
    podcast: Content,
) {
    navController.navigate(Destination.podcast(podcast.id.toString())) { }
}

@Composable
@Preview(name = "Home1")
fun PodcastListDetailScreenPreview() {
    PreviewContent {
        PodcastListDetailScreen()
    }
}

@Composable
@Preview(name = "Home1 (Dark)")
fun PodcastListDetailScreenDarkPreview() {
    PreviewContent(darkTheme = true) {
        PodcastListDetailScreen()
    }
}