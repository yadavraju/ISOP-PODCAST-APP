package com.isop.podcastapp.ui.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.isop.podcastapp.R
import com.isop.podcastapp.ui.common.PrimaryButton

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedButton(visible: Boolean, onClick: () -> Unit) {
    val buttonEnterTransition = fadeIn(
        animationSpec = tween(1000, 2600)
    ) + slideInVertically(
        initialOffsetY = { 100 },
        animationSpec = tween(1000, 2600)
    )

    AnimatedVisibility(
        visible = visible,
        enter = buttonEnterTransition,
    ) {
        PrimaryButton(
            text = stringResource(R.string.get_started),
            onClick = onClick
        )
    }
}