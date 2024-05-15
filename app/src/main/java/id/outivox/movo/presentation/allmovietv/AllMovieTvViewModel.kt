package id.outivox.movo.presentation.allmovietv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.Resource.Companion.init
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.usecase.allmovietv.AllMovieTvUseCase
import id.outivox.core.utils.Constants.INDONESIA
import kotlinx.coroutines.launch

class AllMovieTvViewModel(private val allMovieTvUseCase: AllMovieTvUseCase) : ViewModel() {
    private val _movies = MutableLiveData<Resource<PagingData<Movie>>>()
    val movies get() = _movies

    private val _tvShow = MutableLiveData<Resource<PagingData<Tv>>>()
    val tvShow get() = _tvShow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading get() = _isLoading

    init {
        _movies.value = init()
        _tvShow.value = init()
    }

    fun getMoviesByCategory(category: String) {
        viewModelScope.launch {
            _movies.value = loading()
            allMovieTvUseCase.getMovies(category, INDONESIA, viewModelScope).collect {
                _movies.value = it
            }
        }
    }

    fun getTvShowByCategory(category: String) {
        viewModelScope.launch {
            _tvShow.value = loading()
            allMovieTvUseCase.getTvShow(category).collect {
                _tvShow.value = it
            }
        }
    }
}