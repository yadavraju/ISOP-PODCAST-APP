package com.isop.podcastapp.di

import android.content.Context
import com.isop.podcastapp.data.datastore.PodcastDataStore
import com.isop.podcastapp.data.exoplayer.PodcastMediaSource
import com.isop.podcastapp.data.network.client.EspnPodcastAPIClient
import com.isop.podcastapp.data.network.client.ListenNotesAPIClient
import com.isop.podcastapp.data.network.service.EspnPodcastService
import com.isop.podcastapp.data.network.service.PodcastService
import com.isop.podcastapp.data.service.MediaPlayerServiceConnection
import com.isop.podcastapp.domain.repository.PodcastRepository
import com.isop.podcastapp.domain.repository.PodcastRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHttpClient(): OkHttpClient = ListenNotesAPIClient.createHttpClient()

    @Provides
    @Singleton
    fun providePodcastService(
        client: OkHttpClient,
    ): PodcastService = ListenNotesAPIClient.createPodcastService(client)

    @Named("ESPN")
    @Provides
    fun provideEspnHttpClient(): OkHttpClient = EspnPodcastAPIClient.createHttpClient()

    @Provides
    @Singleton
    fun provideEspnPodcastService(
        @Named("ESPN") client: OkHttpClient,
    ): EspnPodcastService = EspnPodcastAPIClient.createPodcastService(client)

    @Provides
    @Singleton
    fun providePodcastDataStore(
        @ApplicationContext context: Context,
    ): PodcastDataStore = PodcastDataStore(context)

    @Provides
    @Singleton
    fun providePodcastRepository(
        service: PodcastService,
        dataStore: PodcastDataStore,
        espnService: EspnPodcastService
    ): PodcastRepository = PodcastRepositoryImpl(service, dataStore, espnService)

    @Provides
    @Singleton
    fun provideMediaPlayerServiceConnection(
        @ApplicationContext context: Context,
        mediaSource: PodcastMediaSource,
    ): MediaPlayerServiceConnection = MediaPlayerServiceConnection(context, mediaSource)
}