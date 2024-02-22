package id.outivox.core.data.repository.favorite

import id.outivox.core.data.local.LocalDataSource
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.repository.favorite.FavoriteRepository
import id.outivox.core.mapper.MovieMapper.asEntity
import id.outivox.core.mapper.MovieMapper.asModel
import id.outivox.core.mapper.TvMapper.asEntity
import id.outivox.core.mapper.TvMapper.asModel
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(private val localDataSource: LocalDataSource) : FavoriteRepository {
    override fun getFavoriteMovies() = localDataSource.getFavoriteMovies().map { it.asModel() }
    override fun getFavoriteTv() = localDataSource.getFavoriteTv().map { it.asModel() }
    override fun getFavoriteMovieById(id: String) = localDataSource.getFavoriteMovieById(id).map { it.asModel() }
    override fun getFavoriteTvById(id: String) = localDataSource.getFavoriteTvById(id).map { it.asModel() }
    override suspend fun setFavoriteMovie(movie: MovieDetail, newState: Boolean) {
        localDataSource.setFavoriteMovie(movie.asEntity(), newState)
    }
    override suspend fun setFavoriteTv(tv: TvDetail, newState: Boolean) {
        localDataSource.setFavoriteTv(tv.asEntity(), newState)
    }
}