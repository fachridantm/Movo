package id.outivox.core.domain.usecase.home

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getMovies(category: String, region: String, scope: CoroutineScope): Flow<Resource<PagingData<Movie>>>
    fun getTvShow(category: String): Flow<Resource<PagingData<Tv>>>
    fun saveRegion(region: String)
}