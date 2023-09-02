package com.apsl.glideapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.HttpRequestBuilder

class WebSocketSession(
    val httpClient: HttpClient,
    private val block: HttpRequestBuilder.() -> Unit
) {

    suspend fun open(block: HttpRequestBuilder.() -> Unit = this.block): DefaultClientWebSocketSession {
        return httpClient.webSocketSession(block)
    }
}
