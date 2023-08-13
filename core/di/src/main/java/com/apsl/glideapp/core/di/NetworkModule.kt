package com.apsl.glideapp.core.di

import com.apsl.glideapp.core.network.GlideApi
import com.apsl.glideapp.core.network.KtorWebSocketClient
import com.apsl.glideapp.core.network.WebSocketClient
import com.apsl.glideapp.core.util.DispatcherProvider
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
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
    fun provideWebSocketClient(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider
    ): WebSocketClient {
        return KtorWebSocketClient(httpClient, dispatcherProvider)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
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
        val json = Json(builderAction = { ignoreUnknownKeys = true })
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
            .baseUrl("http://10.0.2.2")
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
