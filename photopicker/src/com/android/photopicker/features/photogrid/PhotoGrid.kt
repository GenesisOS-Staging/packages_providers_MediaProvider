/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.photopicker.features.photogrid

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.photopicker.R
import com.android.photopicker.core.components.mediaGrid
import com.android.photopicker.core.features.LocalFeatureManager
import com.android.photopicker.core.navigation.LocalNavController
import com.android.photopicker.core.navigation.PhotopickerDestinations.PHOTO_GRID
import com.android.photopicker.core.selection.LocalSelection
import com.android.photopicker.core.theme.LocalWindowSizeClass
import com.android.photopicker.extensions.navigateToPhotoGrid
import com.android.photopicker.extensions.navigateToPreviewMedia
import com.android.photopicker.features.navigationbar.NavigationBarButton
import com.android.photopicker.features.preview.PreviewFeature

/**
 * Primary composable for drawing the main PhotoGrid on [PhotopickerDestinations.PHOTO_GRID]
 *
 * @param viewModel - A viewModel override for the composable. Normally, this is fetched via hilt
 *   from the backstack entry by using hiltViewModel()
 */
@Composable
fun PhotoGrid(viewModel: PhotoGridViewModel = hiltViewModel()) {

    val navController = LocalNavController.current
    val items = viewModel.data.collectAsLazyPagingItems()
    val featureManager = LocalFeatureManager.current
    val isPreviewEnabled = remember { featureManager.isFeatureEnabled(PreviewFeature::class.java) }

    val state = rememberLazyGridState()

    val selection by LocalSelection.current.flow.collectAsStateWithLifecycle()

    /* Use the expanded layout any time the Width is Medium or larger. */
    val isExpandedScreen: Boolean =
        when (LocalWindowSizeClass.current.widthSizeClass) {
            WindowWidthSizeClass.Medium -> true
            WindowWidthSizeClass.Expanded -> true
            else -> false
        }

    mediaGrid(
        items = items,
        isExpandedScreen = isExpandedScreen,
        selection = selection,
        onItemClick = { item -> viewModel.handleGridItemSelection(item) },
        onItemLongPress = { item ->
            // If the [PreviewFeature] is enabled, launch the preview route.
            if (isPreviewEnabled) {
                navController.navigateToPreviewMedia(item)
            }
        },
        state = state,
    )
}

/**
 * The navigation button for the main photo grid. Composable for
 * [Location.NAVIGATION_BAR_NAV_BUTTON]
 */
@Composable
fun PhotoGridNavButton(modifier: Modifier) {

    val navController = LocalNavController.current

    NavigationBarButton(
        onClick = navController::navigateToPhotoGrid,
        modifier = modifier,
        isCurrentRoute = { route -> route == PHOTO_GRID.route },
    ) {
        Text(stringResource(R.string.photopicker_photos_nav_button_label))
    }
}