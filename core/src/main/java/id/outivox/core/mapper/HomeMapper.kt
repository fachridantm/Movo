package id.outivox.core.mapper

import id.outivox.core.data.remote.source.response.movie.MovieResponse
import id.outivox.core.data.remote.source.response.tv.TvResponse
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.utils.GenreFormatter

object HomeMapper {
    fun MovieResponse.map(): MovieResult {
        val list = results?.map { movie ->
            with(movie) {
                Movie(
                    title ?: originalTitle.orEmpty(),
                    genre = genreIds?.map { genre -> GenreFormatter.format(genre) } ?: listOf(),
                    posterPath = posterPath ?: "/6POBWybSBDBKjSs1VAQcnQC1qyt.jpg",
                    voteAverage = voteAverage ?: 0.0,
                    id = id ?: 0,
                    adult = adult ?: false,
                    overview = overview.orEmpty(),
                    releaseDate = releaseDate.orEmpty(),
                    voteCount = voteCount ?: 0
                )
            }
        }
        return MovieResult(
            page = page ?: 0,
            totalPages = totalPages ?: 0,
            totalResults = totalResults ?: 0,
            movie = list ?: listOf()
        )
    }

    fun TvResponse.map(): TvResult {
        val list = results?.map { tv ->
            with(tv) {
                Tv(
                    firstAirDate = firstAirDate.orEmpty(),
                    genre = genreIds?.map { GenreFormatter.format(it) } ?: listOf(),
                    posterPath = posterPath.orEmpty(),
                    voteAverage =  voteAverage ?: 0.0,
                    name =  name.orEmpty(),
                    id =  id ?: 0
                )
            }
        }
        return TvResult(
            page = page ?: 0,
            totalPages = totalPages ?: 0,
            totalResults =  totalResults ?: 0,
            tv = list ?: listOf()
        )
    }
}
