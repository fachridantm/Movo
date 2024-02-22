package id.outivox.core.domain.repository.favorite

import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteTv(): Flow<List<Tv>>


}