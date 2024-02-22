package id.outivox.core.domain.usecase.favorite

import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavoriteMovies(): Flow<List<MovieDetail>>
    fun getFavoriteTv(): Flow<List<TvDetail>>
    fun getFavoriteMovieById(id: String): Flow<MovieDetail>
    fun getFavoriteTvById(id: String): Flow<TvDetail>
    suspend fun setFavoriteTv(tv: TvDetail, newState: Boolean)

    suspend fun setFavoriteMovie(movie: MovieDetail, newState: Boolean)
}