package id.outivox.core.data.local

import id.outivox.core.data.local.source.room.movie.MovieDao
import id.outivox.core.data.local.source.room.movie.MovieEntity
import id.outivox.core.data.local.source.room.tv.TvDao
import id.outivox.core.data.local.source.room.tv.TvEntity

class LocalDataSource(private val movieDao: MovieDao, private val tvDao: TvDao) {
    fun getFavoriteMovies() = movieDao.getFavoriteMovie()
    fun getFavoriteMovieById(id: String) = movieDao.getFavoriteMovieById(id)
    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        if (newState) {
            movie.isFavorite = newState
            movieDao.apply {
                insertFavoriteMovie(movie)
                updateFavoriteMovie(movie)
            }
        } else {
            movieDao.deleteFavoriteMovie(movie)
        }
    }

    fun getFavoriteTv() = tvDao.getFavoriteTv()
    fun getFavoriteTvById(id: String) = tvDao.getFavoriteTvById(id)
    suspend fun setFavoriteTv(movie: TvEntity, newState: Boolean) {
        if (newState) {
            movie.isFavorite = newState
            tvDao.apply {
                insertFavoriteTv(movie)
                updateFavoriteTv(movie)
            }
        } else {
            tvDao.deleteFavoriteTv(movie)
        }
    }

    fun saveRegion(region: String) {
        // save region to local

    }
}