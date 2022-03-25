package com.isop.podcastapp.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsPadding
import com.isop.podcastapp.data.network.model.podcastdetail.Item
import com.isop.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.isop.podcastapp.data.network.model.podcastlist.Content
import com.isop.podcastapp.ui.common.PreviewContent
import com.isop.podcastapp.ui.common.StaggeredVerticalGrid
import com.isop.podcastapp.ui.common.ViewModelProvider
import com.isop.podcastapp.ui.navigation.Destination
import com.isop.podcastapp.ui.navigation.Navigator
import com.isop.podcastapp.ui.viewmodel.PodcastSearchViewModel
import com.isop.podcastapp.util.Resource

@Composable
fun PodcastListDetailScreen(
    podcastId: String,
) {
    val scrollState = rememberLazyListState()
    val navController = Navigator.current
    val podcastSearchViewModel = ViewModelProvider.podcastSearch
    val podcastListDetails = podcastSearchViewModel.podcastListDetail

    Log.e("Raju", "PodcastListDetailId: $podcastId")

    Surface {
        LazyColumn(state = scrollState) {
            item {
                LargeTitle()
            }

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
                        StaggeredVerticalGrid(
                            crossAxisCount = 2,
                            spacing = 16.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            podcastListDetails.data.content.items.forEach { podcast ->
                                PodcastListDetailView(
                                    podcast = podcastListDetails.data.content,
                                    item = podcast,
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
    podcast: Item,
) {
    navController.navigate(Destination.podcastDetail(podcast.id.toString())) { }
}