package com.apsl.glideapp.core.network.di

import com.apsl.glideapp.core.network.BuildConfig
import com.apsl.glideapp.core.network.http.GlideApi
import com.apsl.glideapp.core.network.util.AuthInterceptor
import com.apsl.glideapp.core.network.websocket.KtorWebSocketClient
import com.apsl.glideapp.core.network.websocket.WebSocketClient
import com.apsl.glideapp.core.network.websocket.WebSocketSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkJson(): Json {
        return Json(DefaultJson) {
            decodeEnumsCaseInsensitive = true
            ignoreUnknownKeys = true
        }
    }

    @MapWebSocket
    @Singleton
    @Provides
    fun provideMapWebSocketSession(httpClient: HttpClient): WebSocketSession {
        return WebSocketSession(httpClient = httpClient) {
            url("${BuildConfig.GLIDE_API_BASE_URL_WS}/api/map")
        }
    }

    @RideWebSocket
    @Singleton
    @Provides
    fun provideRideWebSocketSession(httpClient: HttpClient): WebSocketSession {
        return WebSocketSession(httpClient = httpClient) {
            url("${BuildConfig.GLIDE_API_BASE_URL_WS}/api/ride")
        }
    }

    @Singleton
    @Provides
    fun provideWebSocketClient(
        @MapWebSocket mapWebSocketSession: WebSocketSession,
        @RideWebSocket rideWebSocketSession: WebSocketSession,
        json: Json
    ): WebSocketClient {
        return KtorWebSocketClient(
            mapWebSocketSession = mapWebSocketSession,
            rideWebSocketSession = rideWebSocketSession,
            json = json
        )
    }

    @Singleton
    @Provides
    fun provideHttpClient(engine: HttpClientEngine, json: Json): HttpClient {
        return HttpClient(engine) {
            expectSuccess = true
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(json)
            }
            install(ContentNegotiation) {
                json(json)
            }
            install(DefaultRequest) {
                header("Content-Type", "application/json")
            }
            HttpResponseValidator {
                if (BuildConfig.DEBUG) {
                    validateResponse {
                        Timber.tag("KtorHttpResponse").d(it.toString())
                    }
                }
                handleResponseExceptionWithRequest { cause, request ->
                    Timber.tag("KtorHttpResponse").d(cause)
                }
            }
        }
    }

    @Singleton
    @Provides
    fun provideApi(ktorfit: Ktorfit): GlideApi = ktorfit.create()

    @Singleton
    @Provides
    fun provideKtorfit(httpClient: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(BuildConfig.GLIDE_API_BASE_URL_HTTP)
            .httpClient(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpEngine(authInterceptor: AuthInterceptor): HttpClientEngine {
        return OkHttp.create {
            addInterceptor(authInterceptor)
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
private annotation class MapWebSocket

@Qualifier
@Retention(AnnotationRetention.BINARY)
private annotation class RideWebSocket
