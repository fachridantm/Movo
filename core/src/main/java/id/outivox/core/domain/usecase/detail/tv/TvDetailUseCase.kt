package id.outivox.core.domain.usecase.detail.tv

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.*
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface TvDetailUseCase {
    fun getTvDetail(id: Int): Flow<Resource<TvDetail>>
    fun getSimilarTv(id: Int): Flow<Resource<PagingData<Tv>>>
    fun getTvReviews(id: Int): Flow<Resource<PagingData<Review>>>
    fun getRecommendationsTv(id: Int): Flow<Resource<PagingData<Tv>>>
    fun getTvWallpapers(id: Int): Flow<Resource<Wallpaper>>
    fun getTvActors(id: Int): Flow<Resource<List<Actor>>>
    fun getTvVideos(id: Int): Flow<Resource<List<Video>>>
}