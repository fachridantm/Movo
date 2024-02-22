package id.outivox.core.domain.usecase.home

import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import io.reactivex.rxjava3.core.Flowable

interface HomeUseCase {
    fun getMovies(category: String, page: String): Flowable<Resource<MovieResult>>
    fun getTvShow(category: String, page: String): Flowable<Resource<TvResult>>
    fun saveRegion(region: String)
}