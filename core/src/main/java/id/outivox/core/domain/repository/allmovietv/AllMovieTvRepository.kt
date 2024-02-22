package id.outivox.core.domain.repository.allmovietv

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import kotlinx.coroutines.flow.Flow

interface AllMovieTvRepository {
    fun getMovies(category: String, region: String): Flow<Resource<PagingData<Movie>>>
    fun getTvShow(category: String): Flow<Resource<PagingData<Tv>>>
}