package id.outivox.core.data.local.source.room.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id LIKE :id")
    fun getFavoriteById(id: String): Flow<MovieEntity?>

    @Insert
    suspend fun insertFavoriteMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(movie: MovieEntity)
}