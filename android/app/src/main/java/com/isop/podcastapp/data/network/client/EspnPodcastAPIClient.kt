package com.isop.podcastapp.data.network.client

import com.isop.podcastapp.data.network.constant.ESPN_BASE_URL
import com.isop.podcastapp.data.network.service.EspnPodcastService
import com.isop.podcastapp.data.network.service.PodcastService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EspnPodcastAPIClient {

    fun createHttpClient(): OkHttpClient {
        val httpInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .build()
    }

    fun createPodcastService(
        client: OkHttpClient,
    ): EspnPodcastService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(ESPN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EspnPodcastService::class.java)
    }
}