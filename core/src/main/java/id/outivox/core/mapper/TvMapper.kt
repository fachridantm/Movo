package id.outivox.core.mapper

import id.outivox.core.data.local.source.room.tv.TvEntity
import id.outivox.core.data.remote.source.response.tv.TvItem
import id.outivox.core.data.remote.source.response.tv.TvResponse
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.utils.orZero
import id.outivox.core.utils.toGenreFormat

object TvMapper {

    fun TvResponse.map() = TvResult(
        page = page.orZero(),
        totalPages = totalPages.orZero(),
        totalResults = totalResults.orZero(),
        tv = results?.map { it.map() }.orEmpty()
    )

    fun TvItem.map() = Tv(
        id = id.orZero(),
        title = name.orEmpty(),
        genres = genreIds?.map { it.toGenreFormat() }.orEmpty(),
        posterPath = posterPath.orEmpty(),
        voteAverage = voteAverage.orZero(),
        firstAirDate = firstAirDate.orEmpty()
    )

    fun TvDetail.asEntity() = TvEntity(
        id = id,
        title = title,
        numberOfEpisodes = numberOfEpisodes,
        genres = genres.joinToString(", "),
        numberOfSeasons = numberOfSeasons,
        voteCount = voteCount,
        firstAirDate = firstAirDate,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage
    )

    fun List<TvEntity>.asModel() = map {
        TvDetail(
            id = it.id,
            title = it.title,
            numberOfEpisodes = it.numberOfEpisodes,
            genres = it.genres.split(", "),
            numberOfSeasons = it.numberOfSeasons,
            voteCount = it.voteCount,
            firstAirDate = it.firstAirDate,
            overview = it.overview,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage
        )
    }

    fun TvEntity.asModel() = TvDetail(
        id = id,
        title = title,
        numberOfEpisodes = numberOfEpisodes,
        genres = genres.split(", "),
        numberOfSeasons = numberOfSeasons,
        voteCount = voteCount,
        firstAirDate = firstAirDate,
        overview = overview,
        posterPath = posterPath,
        voteAverage = voteAverage
    )
}