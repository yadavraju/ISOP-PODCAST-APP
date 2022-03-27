package com.espn.podcastapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.espn.podcastapp.data.network.model.podcastdetail.ContentDetails
import com.espn.podcastapp.data.network.model.podcastdetail.Item
import com.espn.podcastapp.data.network.model.podcastdetail.PodcastDetail
import com.espn.podcastapp.data.network.model.podcastlist.EspnPodcastList
import com.espn.podcastapp.domain.repository.PodcastRepository
import com.espn.podcastapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastSearchViewModel @Inject constructor(
    private val repository: PodcastRepository,
) : ViewModel() {

    var podcastList by mutableStateOf<Resource<EspnPodcastList>>(Resource.Loading)
        private set

    var podcastListDetail by mutableStateOf<Resource<PodcastDetail>>(Resource.Loading)
        private set

    init {
        getPodcastsList()
    }

    fun getPodcastListDetail(id: String): Item? {
        return when (podcastListDetail) {
            is Resource.Error -> null
            Resource.Loading -> null
            is Resource.Success -> (podcastListDetail as Resource.Success<PodcastDetail>).data.content.items.find { it.id.toString() == id }
        }
    }

    fun getPodcastListContentDetail(): ContentDetails? {
        return when (podcastListDetail) {
            is Resource.Error -> null
            Resource.Loading -> null
            is Resource.Success -> (podcastListDetail as Resource.Success<PodcastDetail>).data.content
        }
    }

    fun getPodcastsList() {
        viewModelScope.launch {
            podcastList = Resource.Loading
            val result = repository.getPodcastsList()
            result.fold(
                { failure ->
                    podcastList = Resource.Error(failure)
                },
                { data ->
                    podcastList = Resource.Success(data)
                }
            )
        }
    }

    fun getPodcastsListDetail(showId: String) {
        viewModelScope.launch {
            podcastListDetail = Resource.Loading
            val result = repository.getPodcastsListDetail(showId)
            result.fold(
                { failure ->
                    podcastListDetail = Resource.Error(failure)
                },
                { data ->
                    podcastListDetail = Resource.Success(data)
                }
            )
        }
    }
}