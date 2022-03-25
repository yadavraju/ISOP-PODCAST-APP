package com.isop.podcastapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.isop.podcastapp.R
import com.isop.podcastapp.constant.Constant
import com.isop.podcastapp.ui.common.ProvideMultiViewModel
import com.isop.podcastapp.ui.home.HomeScreen
import com.isop.podcastapp.ui.home.PodcastListDetailScreen
import com.isop.podcastapp.ui.navigation.Destination
import com.isop.podcastapp.ui.navigation.Navigator
import com.isop.podcastapp.ui.navigation.ProvideNavHostController
import com.isop.podcastapp.ui.podcast.PodcastBottomBar
import com.isop.podcastapp.ui.podcast.PodcastDetailScreen
import com.isop.podcastapp.ui.podcast.PodcastPlayerScreen
import com.isop.podcastapp.ui.theme.PodcastAppTheme
import com.isop.podcastapp.ui.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PodcastApp)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        var startDestination = Destination.welcome
        if (intent?.action == Constant.ACTION_PODCAST_NOTIFICATION_CLICK) {
            startDestination = Destination.home
        }

        setContent {
            PodcastApp(
                startDestination = startDestination,
                backDispatcher = onBackPressedDispatcher
            )
        }
    }
}

@Composable
fun PodcastApp(
    startDestination: String = Destination.welcome,
    backDispatcher: OnBackPressedDispatcher,
) {
    PodcastAppTheme {
        ProvideWindowInsets {
            ProvideMultiViewModel {
                ProvideNavHostController {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        NavHost(Navigator.current, startDestination) {
                            composable(Destination.welcome) {
                                WelcomeScreen()
                            }

                            composable(Destination.home) {
                                HomeScreen()
                            }

                            composable(
                                route = Destination.podcast,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { backStackEntry ->
                                PodcastListDetailScreen(
                                    podcastId = backStackEntry.arguments?.getString("id")!!,
                                )
                            }

                            composable(
                                route = Destination.podcastDetail,
                                arguments = listOf(navArgument("id") { type = NavType.StringType })
                            ) { backStackEntry ->
                                PodcastDetailScreen(
                                    podcastId = backStackEntry.arguments?.getString("id")!!,
                                )
                            }
                        }
                        PodcastBottomBar(
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                        PodcastPlayerScreen(backDispatcher)
                    }
                }
            }
        }
    }
}
