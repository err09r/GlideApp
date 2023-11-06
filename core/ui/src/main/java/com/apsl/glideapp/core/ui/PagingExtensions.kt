package com.apsl.glideapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow

@Suppress("ComposableNaming")
@Composable
fun <T : Any> Flow<PagingData<T>>.receiveAsLazyPagingItems(action: (LazyPagingItems<T>) -> Unit) {
    val lazyPagingItems = this.collectAsLazyPagingItems()
    LaunchedEffect(lazyPagingItems.loadState) {
        action(lazyPagingItems)
    }
}

fun <T : Any> LazyPagingItems<T>.peekOrNull(index: Int): T? {
    return try {
        this.peek(index)
    } catch (e: Exception) {
        null
    }
}
