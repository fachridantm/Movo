package id.outivox.movo.presentation.allmovietv

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.usecase.allmovietv.AllMovieTvUseCase
import id.outivox.core.utils.Constants.INDONESIA

class AllMovieTvViewModel(private val allMovieTvUseCase: AllMovieTvUseCase) : ViewModel() {
    val movieResponse = MediatorLiveData<Resource<MovieResult>>()
    val tvResponse = MediatorLiveData<Resource<TvResult>>()

    fun getMoviesByCategory(category: String, page: String = "1") {
        val source = allMovieTvUseCase.getMovies(category, page, INDONESIA).toLiveData()

        movieResponse.addSource(source) {
            movieResponse.postValue(it)
            movieResponse.removeSource(source)
        }
    }

    fun getTvByCategory(category: String, page: String = "1") {
        val source = allMovieTvUseCase.getTvShow(category, page, INDONESIA).toLiveData()

        tvResponse.addSource(source) {
            tvResponse.postValue(it)
            tvResponse.removeSource(source)
        }
    }
}