package id.outivox.core.mapper

import id.outivox.core.data.remote.source.response.tv.TvItem
import id.outivox.core.data.remote.source.response.tv.TvResponse
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
        genre = genreIds?.map { it.toGenreFormat() }.orEmpty(),
        posterPath = posterPath.orEmpty(),
        voteAverage = voteAverage.orZero(),
        firstAirDate = firstAirDate.orEmpty()
    )
}