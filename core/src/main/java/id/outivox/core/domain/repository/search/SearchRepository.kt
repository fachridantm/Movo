package id.outivox.core.domain.repository.search

import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import io.reactivex.rxjava3.core.Flowable

interface SearchRepository {
    fun searchMovie(query: String, page: String): Flowable<Resource<MovieResult>>
    fun searchTvShow(query: String, page: String): Flowable<Resource<TvResult>>
}