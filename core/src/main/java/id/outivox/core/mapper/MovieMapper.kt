package id.outivox.core.mapper

import id.outivox.core.data.local.source.room.movie.MovieEntity
import id.outivox.core.data.remote.source.response.detail.movie.MovieDetailResponse
import id.outivox.core.data.remote.source.response.movie.MovieItem
import id.outivox.core.data.remote.source.response.movie.MovieResponse
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.utils.orFalse
import id.outivox.core.utils.orZero
import id.outivox.core.utils.toGenreFormat

object MovieMapper {

    fun MovieItem.map() = Movie(
        id = id.orZero(),
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        genres = genreIds?.map { it.toGenreFormat() }.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        posterPath = posterPath.orEmpty(),
        voteAverage = voteAverage.orZero(),
        voteCount = voteCount.orZero(),
        adult = adult.orFalse()
    )

    fun MovieResponse.map() = MovieResult(
        page = page.orZero(),
        totalPages = totalPages.orZero(),
        totalResults = totalResults.orZero(),
        movie = results?.map { movie ->
            with(movie) {
                Movie(
                    id = id.orZero(),
                    title = title.orEmpty(),
                    overview = overview.orEmpty(),
                    genres = genreIds?.map { it.toGenreFormat() }.orEmpty(),
                    releaseDate = releaseDate.orEmpty(),
                    posterPath = posterPath.orEmpty(),
                    voteAverage = voteAverage.orZero(),
                    voteCount = voteCount.orZero(),
                    adult = adult.orFalse()
                )
            }
        }.orEmpty()
    )

    fun MovieDetailResponse.map() = MovieDetail(
        id = id.orZero(),
        video = video ?: false,
        title = title.orEmpty(),
        genres = genres?.map { it.id.toGenreFormat() }.orEmpty(),
        voteCount = voteCount.orZero(),
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage.orZero(),
        duration = runtime.orZero()
    )

    fun MovieDetail.asEntity() = MovieEntity(
        id = id,
        title = title,
        genres = genres.joinToString(", "),
        overview = overview,
        video = video,
        duration = duration,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteCount = voteCount,
        voteAverage = voteAverage,
    )

    fun List<MovieEntity>.asModel() = map {
        MovieDetail(
            id = it.id,
            title = it.title,
            genres = it.genres.split(", "),
            overview = it.overview,
            video = it.video,
            duration = it.duration,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            voteCount = it.voteCount,
            voteAverage = it.voteAverage,
        )
    }

    fun MovieEntity.asModel() = MovieDetail(
        id = id,
        title = title,
        genres = genres.split(", "),
        overview = overview,
        video = video,
        duration = duration,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteCount = voteCount,
        voteAverage = voteAverage,
    )
}