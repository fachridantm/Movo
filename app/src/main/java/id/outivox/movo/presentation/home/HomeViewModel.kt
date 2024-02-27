package id.outivox.movo.presentation.home

import androidx.lifecycle.LiveData
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

    private val _airingTodayTv = MutableLiveData<Resource<PagingData<Tv>>>()
    val airingTodayTv get() = _airingTodayTv

    private val _popularTv = MutableLiveData<Resource<PagingData<Tv>>>()
    val popularTv get() = _popularTv

    init {
        _nowPlayingMovies.value = init()
        _popularMovies.value = init()
        _upcomingMovies.value = init()
        _airingTodayTv.value = init()
        _popularTv.value = init()
    }

    fun getNowPlayingMovies() {
        viewModelScope.launch {
            _nowPlayingMovies.value = loading()
            homeUseCase.getMovies(NOW_PLAYING_MOVIE, INDONESIA).collect {
                _nowPlayingMovies.value = it
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _popularMovies.value = loading()
            homeUseCase.getMovies(POPULAR_MOVIE, INDONESIA).collect {
                _popularMovies.value = it
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch {
            _upcomingMovies.value = loading()
            homeUseCase.getMovies(UPCOMING_MOVIE, INDONESIA).collect {
                _upcomingMovies.value = it
            }
        }
    }

    fun getAiringTodayTv() {
        viewModelScope.launch {
            _airingTodayTv.value = loading()
            homeUseCase.getTvShow(AIRING_TODAY_TV).collect {
                _airingTodayTv.value = it
            }
        }
    }

    fun getPopularTv() {
        viewModelScope.launch {
            _popularTv.value = loading()
            homeUseCase.getTvShow(POPULAR_TV).collect {
                _popularTv.value = it
            }
        }
    }
}