package id.outivox.movo.presentation.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.usecase.search.SearchUseCase

class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    val movieResponse = MediatorLiveData<Resource<MovieResult>>()
    val tvResponse = MediatorLiveData<Resource<TvResult>>()

    fun searchMovie(query: String, page: String = "1") {
        val source = searchUseCase.searchMovie(query, page).toLiveData()

        movieResponse.addSource(source) {
            movieResponse.postValue(it)
            movieResponse.removeSource(source)
        }
    }

    fun searchTv(query: String, page: String = "1") {
        val source = searchUseCase.searchTvShow(query, page).toLiveData()

        tvResponse.addSource(source) {
            tvResponse.postValue(it)
            tvResponse.removeSource(source)
        }
    }
}