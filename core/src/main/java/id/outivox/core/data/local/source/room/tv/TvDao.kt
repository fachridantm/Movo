package id.outivox.core.data.local.source.room.tv

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {

    @Query("SELECT * FROM TvEntity WHERE is_favorite = 1")
    fun getFavoriteTv(): Flow<List<TvEntity>>

    @Query("SELECT * FROM TvEntity WHERE id LIKE :id")
    fun getFavoriteTvById(id: String): Flow<TvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteTv(tv: TvEntity)

    @Update
    suspend fun updateFavoriteTv(deal: TvEntity)

    @Delete
    suspend fun deleteFavoriteTv(tv: TvEntity)
}