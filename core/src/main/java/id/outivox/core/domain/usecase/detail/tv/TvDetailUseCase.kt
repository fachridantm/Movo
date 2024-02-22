package id.outivox.core.domain.usecase.detail.tv

import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.*
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface TvDetailUseCase {
    fun getTvDetail(id: String): Flow<Resource<TvDetail>>
    fun getTvReviews(id: String): Flow<Resource<List<Review>>>
    fun getTvWallpapers(id: String): Flow<Resource<Wallpaper>>
    fun getTvActors(id: String, region: String): Flow<Resource<List<Actor>>>
    fun getTvVideos(id: String): Flow<Resource<List<Video>>>
    fun getRecommendationsTv(id: String, region: String): Flow<Resource<TvResult>>
    fun getSimilarTv(id: String, region: String): Flow<Resource<TvResult>>
}