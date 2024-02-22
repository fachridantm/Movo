package id.outivox.core.data.repository.favorite

import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.repository.favorite.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl: FavoriteRepository {
    override fun getFavoriteMovies(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteTv(): Flow<List<Tv>> {
        TODO("Not yet implemented")
    }

}