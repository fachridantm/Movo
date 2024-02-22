package id.outivox.core.data.local.source.room.tv

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TvEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "genres") val genres: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "number_of_seasons") val numberOfSeasons: Int,
    @ColumnInfo(name = "number_of_episodes") val numberOfEpisodes: Int,
    @ColumnInfo(name = "first_air_date") val firstAirDate: String,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
): Parcelable
