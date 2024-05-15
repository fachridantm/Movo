package id.outivox.movo.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.Resource.Companion.init
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.usecase.home.HomeUseCase
import id.outivox.core.utils.Constants.AIRING_TODAY_TV
import id.outivox.core.utils.Constants.INDONESIA
import id.outivox.core.utils.Constants.NOW_PLAYING_MOVIE
import id.outivox.core.utils.Constants.POPULAR_MOVIE
import id.outivox.core.utils.Constants.POPULAR_TV
import id.outivox.core.utils.Constants.UPCOMING_MOVIE
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {
    private val _nowPlayingMovies = MutableLiveData<Resource<PagingData<Movie>>>()
    val nowPlayingMovies get() = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<Resource<PagingData<Movie>>>()
    val popularMovies get() = _popularMovies

    private val _upcomingMovies = MutableLiveData<Resource<PagingData<Movie>>>()
    val upcomingMovies get() = _upcomingMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading get() = _isLoading

    init {
        _nowPlayingMovies.value = init()
        _popularMovies.value = init()
        _upcomingMovies.value = init()
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            _nowPlayingMovies.value = loading()
            homeUseCase.getMovies(NOW_PLAYING_MOVIE, INDONESIA, viewModelScope).collect {
                _nowPlayingMovies.value = it
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _popularMovies.value = loading()
            homeUseCase.getMovies(POPULAR_MOVIE, INDONESIA, viewModelScope).collect {
                _popularMovies.value = it
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMovies.value = loading()
            homeUseCase.getMovies(UPCOMING_MOVIE, INDONESIA, viewModelScope).collect {
                _upcomingMovies.value = it
            }
        }
    }
}