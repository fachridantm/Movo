package id.outivox.movo.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.Resource.Companion.init
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.usecase.search.SearchUseCase
import id.outivox.core.utils.Constants.INDONESIA
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _movies = MutableLiveData<Resource<PagingData<Movie>>>()
    val movies get() = _movies

    private val _tvShow = MutableLiveData<Resource<PagingData<Tv>>>()
    val tvShow get() = _tvShow

    init {
        _movies.value = init()
        _tvShow.value = init()
    }

    fun searchMovie(query: String) {
        viewModelScope.launch {
            _movies.value = loading()
            searchUseCase.searchMovie(query, INDONESIA).collect {
                _movies.value = it
            }
        }
    }

    fun searchTv(query: String) {
        viewModelScope.launch {
            _tvShow.value = loading()
            searchUseCase.searchTvShow(query).collect {
                _tvShow.value = it
            }
        }
    }
}