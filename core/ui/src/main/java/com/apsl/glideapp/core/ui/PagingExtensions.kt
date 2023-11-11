@file:Suppress("Unused, ComposableNaming")

package com.apsl.glideapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.apsl.glideapp.common.util.UUID
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : Any> Flow<PagingData<T>>.receiveAsLazyPagingItems(action: (LazyPagingItems<T>) -> Unit) {
    val lazyPagingItems = this.collectAsLazyPagingItems()
    LaunchedEffect(lazyPagingItems.loadState) {
        action(lazyPagingItems)
    }
}

/**
 * Returns the presented item at the specified position, without notifying Paging of the item
 * access that would normally trigger page loads.
 *
 * @param index Index of the presented item to return, including placeholders.
 * @return The presented item at position [index], `null` if it is a placeholder or if the item
 * does not exist.
 */
fun <T : Any> LazyPagingItems<T>.peekOrNull(index: Int): T? {
    return try {
        this.peek(index)
    } catch (e: Exception) {
        null
    }
}

@Immutable
@JvmInline
value class ComposePagingItems<T : Any>(val value: LazyPagingItems<T>) {
    val itemCount: Int get() = value.itemCount
    operator fun get(index: Int): T? = value[index]
    fun peek(index: Int): T? = value.peek(index)
    fun peekOrNull(index: Int): T? = value.peekOrNull(index)
    fun refresh(): Unit = value.refresh()
}

fun <T : Any> LazyPagingItems<T>.toComposePagingItems(): ComposePagingItems<T> {
    return ComposePagingItems(this)
}

@Immutable
data class PagingSeparator(val text: String, val id: String = UUID.randomUUID().toString())
