package id.outivox.core.mapper

import id.outivox.core.data.local.source.room.movie.MovieEntity
import id.outivox.core.data.remote.source.response.detail.movie.MovieDetailResponse
import id.outivox.core.domain.model.detail.*
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.utils.GenreFormatter

object MovieDetailMapper {
    fun MovieDetailResponse.map(): MovieDetail{
        return MovieDetail(
            id = id ?: 0,
            video = video ?: false,
            title = title.orEmpty(),
            genres = genres?.map { GenreFormatter.format(it.id ?: 0) } ?: listOf(),
            voteCount = voteCount ?: 0,
            overview = overview.orEmpty(),
            posterPath = posterPath.orEmpty(),
            releaseDate = releaseDate.orEmpty(),
            voteAverage = voteAverage ?: 0.0,
            duration = runtime ?: 0
        )
    }

    fun Movie.asEntity(): MovieEntity{
        return MovieEntity(
            id = id,
            title = title,
            genres = genre.toString(),
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            voteCount = voteCount,
            voteAverage = voteAverage
        )
    }
}