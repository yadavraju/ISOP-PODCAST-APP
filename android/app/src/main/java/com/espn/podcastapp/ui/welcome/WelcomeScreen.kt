package com.espn.podcastapp.ui.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.espn.podcastapp.ui.common.PreviewContent
import com.espn.podcastapp.ui.navigation.Destination
import com.espn.podcastapp.ui.navigation.Navigator

@Composable
fun WelcomeScreen() {
    var visible by remember { mutableStateOf(false) }
    val navController = Navigator.current

    LaunchedEffect(true) {
        visible = true
    }

    WelcomeScreenContent(visible = visible) {
        navController.navigate(Destination.home) {
            popUpTo(Destination.welcome) {
                saveState = true
                inclusive = true
            }
        }
    }
}

@Composable
fun WelcomeScreenContent(
    visible: Boolean,
    onGetStarted: () -> Unit,
) {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedTitle(visible = visible)

            //AnimatedImage(visible = visible)

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedButton(visible = visible, onClick = onGetStarted)
        }
    }
}

@Preview(name = "Welcome")
@Composable
fun WelcomeScreenPreview() {
    PreviewContent {
        WelcomeScreenContent(visible = true) { }
    }
}

@Preview(name = "Welcome (Dark)")
@Composable
fun WelcomeScreenDarkPreview() {
    PreviewContent(darkTheme = true) {
        WelcomeScreenContent(visible = true) { }
    }
}