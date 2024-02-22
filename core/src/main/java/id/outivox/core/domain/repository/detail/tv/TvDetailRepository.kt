package id.outivox.core.domain.repository.detail.tv

import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.domain.model.tv.TvResult
import kotlinx.coroutines.flow.Flow

interface TvDetailRepository {
    fun getTvDetail(id: String): Flow<Resource<TvDetail>>
    fun getTvReviews(id: String): Flow<Resource<List<Review>>>
    fun getTvWallpapers(id: String): Flow<Resource<Wallpaper>>
    fun getTvActors(id: String, region: String): Flow<Resource<List<Actor>>>
    fun getTvVideos(id: String): Flow<Resource<List<Video>>>
    fun getRecommendationsTv(id: String, region: String): Flow<Resource<TvResult>>
    fun getSimilarTv(id: String, region: String): Flow<Resource<TvResult>>
}