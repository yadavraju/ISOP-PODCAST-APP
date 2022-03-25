package com.isop.podcastapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isop.podcastapp.data.network.model.podcastlist.EspnPodcastListDto
import com.isop.podcastapp.domain.model.Episode
import com.isop.podcastapp.domain.model.PodcastSearch
import com.isop.podcastapp.domain.repository.PodcastRepository
import com.isop.podcastapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastSearchViewModel @Inject constructor(
    private val repository: PodcastRepository,
) : ViewModel() {

    var podcastSearch by mutableStateOf<Resource<PodcastSearch>>(Resource.Loading)
        private set

    var podcastList by mutableStateOf<Resource<EspnPodcastListDto>>(Resource.Loading)
        private set

    init {
        //searchPodcasts()
        getPodcastsList()
    }

    fun getPodcastDetail(id: String): Episode? {
        return when (podcastSearch) {
            is Resource.Error -> null
            Resource.Loading -> null
            is Resource.Success -> (podcastSearch as Resource.Success<PodcastSearch>).data.results.find { it.id == id }
        }
    }

    fun searchPodcasts() {
        viewModelScope.launch {
            podcastSearch = Resource.Loading
            val result = repository.searchPodcasts("fiction", "episode")
            result.fold(
                { failure ->
                    podcastSearch = Resource.Error(failure)
                },
                { data ->
                    podcastSearch = Resource.Success(data)
                }
            )
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
}