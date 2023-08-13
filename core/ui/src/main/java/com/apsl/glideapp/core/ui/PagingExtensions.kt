package com.apsl.glideapp.core.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow

@SuppressLint("ComposableNaming")
@Composable
fun <T : Any> Flow<PagingData<T>>.receiveAsLazyPagingItems(action: (LazyPagingItems<T>) -> Unit) {
    val lazyPagingItems = this.collectAsLazyPagingItems()
    LaunchedEffect(lazyPagingItems.loadState) {
        action(lazyPagingItems)
    }
}
