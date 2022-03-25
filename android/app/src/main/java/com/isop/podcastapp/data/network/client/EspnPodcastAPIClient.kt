package com.isop.podcastapp.data.network.client

import com.isop.podcastapp.data.network.service.EspnPodcastService
import com.isop.podcastapp.data.network.service.EspnPodcastListDetailService
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
        baseUrl: String,
    ): EspnPodcastService = retrofitBuilder(client, baseUrl).create(EspnPodcastService::class.java)

    fun createPodcastListDetailService(
        client: OkHttpClient,
        baseUrl: String,
    ): EspnPodcastListDetailService =
        retrofitBuilder(client, baseUrl).create(EspnPodcastListDetailService::class.java)

    private fun retrofitBuilder(
        client: OkHttpClient,
        baseUrl: String,
    ) = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}