package id.outivox.core.mapper

import id.outivox.core.data.remote.source.response.detail.actor.ActorResponse
import id.outivox.core.data.remote.source.response.detail.review.ReviewItem
import id.outivox.core.data.remote.source.response.detail.video.VideoResponse
import id.outivox.core.data.remote.source.response.detail.wallpaper.BackdropsItem
import id.outivox.core.data.remote.source.response.detail.wallpaper.WallpaperResponse
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.utils.orFalse
import id.outivox.core.utils.orZero

object DetailsMapper {
    fun ReviewItem.map() = Review(
        avatarPath = authorDetails?.avatarPath.orEmpty(),
        name = author.orEmpty(),
        rating = authorDetails?.rating.orZero(),
        username = author.orEmpty(),
        content = content.orEmpty()
    )

    fun ActorResponse.map() = cast?.map {
        Actor(
            character = it.character.orEmpty(),
            name = it.name.orEmpty(),
            profilePath = it.profilePath.orEmpty()
        )
    }.orEmpty()

    fun VideoResponse.map() = results?.map {
        Video(
            name = it.name.orEmpty(),
            official = it.official.orFalse(),
            id = it.id.orEmpty(),
            key = it.key.orEmpty()
        )
    }.orEmpty()

    fun WallpaperResponse.map() = Wallpaper(
        wallpaperUrl = backdrops?.map {
            it.filePath.orEmpty()
        }.orEmpty(),
        posterUrl = posters?.map {
            it.filePath.orEmpty()
        }.orEmpty()
    )
}