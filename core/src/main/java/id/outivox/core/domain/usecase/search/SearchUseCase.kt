package id.outivox.core.domain.usecase.search

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow


interface SearchUseCase {
    fun searchMovie(query: String, region: String): Flow<Resource<PagingData<Movie>>>
    fun searchTvShow(query: String): Flow<Resource<PagingData<Tv>>>
}