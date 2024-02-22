package id.outivox.movo.presentation.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import id.outivox.core.utils.Constants.NOW_PLAYING_MOVIE
import id.outivox.core.utils.Constants.POPULAR_MOVIE
import id.outivox.core.utils.Constants.UPCOMING_MOVIE
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.usecase.home.HomeUseCase

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    var nowPlayingResponse = MediatorLiveData<Resource<MovieResult>>()
    var popularResponse = MediatorLiveData<Resource<MovieResult>>()
    var upcomingResponse = MediatorLiveData<Resource<MovieResult>>()

    private var currentPage = MutableLiveData(1)

    fun getNowPlayingMovies() {
        val source = homeUseCase.getMovies(NOW_PLAYING_MOVIE, currentPage.value.toString()).toLiveData()

        nowPlayingResponse.addSource(source){
            nowPlayingResponse.postValue(it)
            nowPlayingResponse.removeSource(source)
        }
    }

    fun getPopularMovies() {
        val source = homeUseCase.getMovies(POPULAR_MOVIE, currentPage.value.toString()).toLiveData()

        popularResponse.addSource(source){
            popularResponse.postValue(it)
            popularResponse.removeSource(source)
        }
    }

    fun getUpComingMovies() {
        val source = homeUseCase.getMovies(UPCOMING_MOVIE, currentPage.value.toString()).toLiveData()

        upcomingResponse.addSource(source){
            upcomingResponse.postValue(it)
            upcomingResponse.removeSource(source)
        }
    }
}