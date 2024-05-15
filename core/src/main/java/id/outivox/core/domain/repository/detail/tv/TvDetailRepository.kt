package id.outivox.core.domain.repository.detail.tv

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.domain.model.tv.Tv
import kotlinx.coroutines.flow.Flow

interface TvDetailRepository {
    fun getTvDetail(id: Int): Flow<Resource<TvDetail>>
    fun getSimilarTv(id: Int): Flow<Resource<PagingData<Tv>>>
    fun getTvReviews(id: Int): Flow<Resource<PagingData<Review>>>
    fun getRecommendationsTv(id: Int): Flow<Resource<PagingData<Tv>>>
    fun getTvWallpapers(id: Int): Flow<Resource<Wallpaper>>
    fun getTvActors(id: Int): Flow<Resource<List<Actor>>>
    fun getTvVideos(id: Int): Flow<Resource<List<Video>>>
}
