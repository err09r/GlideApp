package com.apsl.glideapp.core.network.di

import com.apsl.glideapp.core.network.BuildConfig
import com.apsl.glideapp.core.network.http.GlideApi
import com.apsl.glideapp.core.network.util.AuthInterceptor
import com.apsl.glideapp.core.network.websocket.KtorWebSocketClient
import com.apsl.glideapp.core.network.websocket.WebSocketClient
import com.apsl.glideapp.core.network.websocket.WebSocketSession
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.url
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.DefaultJson
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

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
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        json: Json
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                addInterceptor(authInterceptor)
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(json)
            }
        }
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): GlideApi {
        return retrofit.create(GlideApi::class.java)
    }

    @Singleton
    @Provides
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        jsonConverterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GLIDE_API_BASE_URL_HTTP)
            .addConverterFactory(jsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(authInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
private annotation class MapWebSocket

@Qualifier
@Retention(AnnotationRetention.BINARY)
private annotation class RideWebSocket
