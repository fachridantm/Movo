package id.outivox.core.domain.usecase.favorite

import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.repository.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteInteractor(
    private val favoriteRepository: FavoriteRepository
): FavoriteUseCase {
    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteRepository.getFavoriteMovies()
    }

    override fun getFavoriteTv(): Flow<List<Tv>> {
        return favoriteRepository.getFavoriteTv()
    }
}