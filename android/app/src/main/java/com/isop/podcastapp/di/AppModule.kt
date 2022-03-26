package com.isop.podcastapp.di

import android.content.Context
import com.isop.podcastapp.data.datastore.PodcastDataStore
import com.isop.podcastapp.data.exoplayer.PodcastMediaSource
import com.isop.podcastapp.data.network.client.EspnPodcastAPIClient
import com.isop.podcastapp.data.network.constant.ESPN_BASE_URL
import com.isop.podcastapp.data.network.constant.PODCAST_LIST_DETAIL_BASE_URL
import com.isop.podcastapp.data.network.service.EspnPodcastListDetailService
import com.isop.podcastapp.data.network.service.EspnPodcastService
import com.isop.podcastapp.data.service.MediaPlayerServiceConnection
import com.isop.podcastapp.domain.repository.PodcastRepository
import com.isop.podcastapp.domain.repository.PodcastRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideEspnHttpClient(): OkHttpClient = EspnPodcastAPIClient.createHttpClient()

    @Provides
    @Singleton
    fun provideEspnPodcastService(
        client: OkHttpClient,
    ): EspnPodcastService = EspnPodcastAPIClient.createPodcastService(client, ESPN_BASE_URL)


    @Provides
    @Singleton
    fun provideEspnPodcastListService(
        client: OkHttpClient,
    ): EspnPodcastListDetailService =
        EspnPodcastAPIClient.createPodcastListDetailService(client, PODCAST_LIST_DETAIL_BASE_URL)

    @Provides
    @Singleton
    fun providePodcastDataStore(
        @ApplicationContext context: Context,
    ): PodcastDataStore = PodcastDataStore(context)

    @Provides
    @Singleton
    fun providePodcastRepository(
        dataStore: PodcastDataStore,
        espnService: EspnPodcastService,
        espnListService: EspnPodcastListDetailService,
    ): PodcastRepository = PodcastRepositoryImpl(dataStore, espnService, espnListService)

    @Provides
    @Singleton
    fun provideMediaPlayerServiceConnection(
        @ApplicationContext context: Context,
        mediaSource: PodcastMediaSource,
    ): MediaPlayerServiceConnection = MediaPlayerServiceConnection(context, mediaSource)
}