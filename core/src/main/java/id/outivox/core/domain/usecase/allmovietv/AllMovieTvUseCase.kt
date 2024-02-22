package id.outivox.core.domain.usecase.allmovietv

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import kotlinx.coroutines.flow.Flow

interface AllMovieTvUseCase {
    fun getMovies(category: String, region: String): Flow<Resource<PagingData<Movie>>>
    fun getTvShow(category: String): Flow<Resource<PagingData<Tv>>>
}