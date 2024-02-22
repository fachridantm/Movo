package id.outivox.core.domain.usecase.favorite

import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.repository.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor(private val favoriteRepository: FavoriteRepository): FavoriteUseCase {
    override fun getFavoriteMovies() = favoriteRepository.getFavoriteMovies()
    override fun getFavoriteTv() = favoriteRepository.getFavoriteTv()
    override fun getFavoriteMovieById(id: String) = favoriteRepository.getFavoriteMovieById(id)
    override fun getFavoriteTvById(id: String) = favoriteRepository.getFavoriteTvById(id)
    override suspend fun setFavoriteTv(tv: TvDetail, newState: Boolean) = favoriteRepository.setFavoriteTv(tv, newState)
    override suspend fun setFavoriteMovie(movie: MovieDetail, newState: Boolean) = favoriteRepository.setFavoriteMovie(movie, newState)
}