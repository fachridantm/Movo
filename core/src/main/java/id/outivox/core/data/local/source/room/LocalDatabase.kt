package id.outivox.core.data.local.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.outivox.core.data.local.source.room.movie.MovieDao
import id.outivox.core.data.local.source.room.movie.MovieEntity
import id.outivox.core.data.local.source.room.tv.TvDao
import id.outivox.core.data.local.source.room.tv.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 1)
abstract class LocalDatabase: RoomDatabase(){
    abstract val movieDao: MovieDao
    abstract val tvDao: TvDao
}
