package com.espn.podcastapp.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsPadding
import com.espn.podcastapp.ui.common.PreviewContent
import com.espn.podcastapp.ui.common.StaggeredVerticalGrid
import com.espn.podcastapp.ui.common.ViewModelProvider
import com.espn.podcastapp.ui.navigation.Destination
import com.espn.podcastapp.ui.navigation.Navigator
import com.espn.podcastapp.ui.viewmodel.PodcastSearchViewModel
import com.espn.podcastapp.util.Resource

@Composable
fun HomeScreen() {
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
                                    openPodcastDetail(navController,
                                        podcast.id.toString(),
                                        podcastSearchViewModel)
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
    id: String,
    podcastSearchViewModel: PodcastSearchViewModel,
) {
    navController.navigate(Destination.podcast(id)) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // re selecting the same item
        launchSingleTop = true
        // Restore state when re selecting a previously selected item
        restoreState = true
        podcastSearchViewModel.getPodcastsListDetail(id)
    }
}

@Composable
@Preview(name = "Home")
fun HomeScreenPreview() {
    PreviewContent {
        HomeScreen()
    }
}

@Composable
@Preview(name = "Home (Dark)")
fun HomeScreenDarkPreview() {
    PreviewContent(darkTheme = true) {
        HomeScreen()
    }
}