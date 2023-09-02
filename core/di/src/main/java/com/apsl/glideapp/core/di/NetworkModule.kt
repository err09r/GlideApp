package com.apsl.glideapp.core.di

import com.apsl.glideapp.core.network.GlideApi
import com.apsl.glideapp.core.network.KtorWebSocketClient
import com.apsl.glideapp.core.network.WebSocketClient
import com.apsl.glideapp.core.network.WebSocketSession
import com.apsl.glideapp.core.util.DispatcherProvider
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
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MapWebSocket

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RideWebSocket

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @MapWebSocket
    @Singleton
    @Provides
    fun provideMapWebSocketSession(httpClient: HttpClient): WebSocketSession {
        return WebSocketSession(httpClient = httpClient) {
            url("ws://192.168.1.120/api/map")
        }
    }

    @RideWebSocket
    @Singleton
    @Provides
    fun provideRideWebSocketSession(httpClient: HttpClient): WebSocketSession {
        return WebSocketSession(httpClient = httpClient) {
            url("ws://192.168.1.120/api/ride")
        }
    }

    @Singleton
    @Provides
    fun provideWebSocketClient(
        @MapWebSocket mapWebSocketSession: WebSocketSession,
        @RideWebSocket rideWebSocketSession: WebSocketSession,
        dispatcherProvider: DispatcherProvider
    ): WebSocketClient {
        return KtorWebSocketClient(
            mapWebSocketSession = mapWebSocketSession,
            rideWebSocketSession = rideWebSocketSession,
            dispatchers = dispatcherProvider
        )
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                addInterceptor(authInterceptor)
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
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
    fun provideJsonConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        jsonConverterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
//            .baseUrl(BuildConfig.GLIDE_API_BASE_URL_HTTP)
//            .baseUrl("http://10.0.2.2")
            .baseUrl("http://192.168.1.120/")
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
