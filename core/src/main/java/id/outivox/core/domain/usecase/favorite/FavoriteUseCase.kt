package id.outivox.core.domain.usecase.favorite

import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteTv(): Flow<List<Tv>>
}