package id.outivox.core.domain.usecase.search

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import kotlinx.coroutines.flow.Flow


interface SearchUseCase {
    fun searchMovie(query: String, region: String): Flow<Resource<PagingData<Movie>>>
    fun searchTvShow(query: String): Flow<Resource<PagingData<Tv>>>
}