/*
 * Copyright (C) 2024 The Android Open Source Project
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

package com.android.photopicker.data.paging

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.photopicker.data.model.Media
import com.android.photopicker.data.model.MediaPageKey

/**
 * This [PagingSource] class is responsible to providing paginated album media data from Picker
 * Database by serving requests from Paging library.
 *
 * It sources data from a [ContentProvider] called [MediaProvider].
 */
class AlbumContentPagingSource(
        context: Context,
        albumId: String,
) : PagingSource<MediaPageKey, Media>() {

    override suspend fun load(
            params: LoadParams<MediaPageKey>
    ): LoadResult<MediaPageKey, Media> =
            throw NotImplementedError("This method is not implemented yet.")

    override fun getRefreshKey(state: PagingState<MediaPageKey, Media>): MediaPageKey? =
            throw NotImplementedError("This method is not implemented yet.")
}