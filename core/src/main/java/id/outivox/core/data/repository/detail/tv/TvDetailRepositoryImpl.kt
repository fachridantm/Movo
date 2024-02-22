package id.outivox.core.data.repository.detail.tv

import id.outivox.core.data.NetworkBoundResource
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.response.detail.actor.ActorResponse
import id.outivox.core.data.remote.source.response.detail.review.ReviewResponse
import id.outivox.core.data.remote.source.response.detail.tv.TvDetailResponse
import id.outivox.core.data.remote.source.response.detail.video.VideoResponse
import id.outivox.core.data.remote.source.response.detail.wallpaper.WallpaperResponse
import id.outivox.core.data.remote.source.response.tv.TvResponse
import id.outivox.core.data.repository.detail.movie.MovieDetailRepositoryImpl
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.repository.detail.tv.TvDetailRepository
import id.outivox.core.mapper.HomeMapper.map
import io.reactivex.rxjava3.core.Flowable

class TvDetailRepositoryImpl(val remoteDataSource: RemoteDataSource) : TvDetailRepository {
    override fun getTvDetail(id: String): Flowable<Resource<TvDetail>> {
        return object : NetworkBoundResource<TvDetail, TvDetailResponse>() {
            override fun createResult(data: TvDetailResponse) = data.map()
            override fun createCall() = remoteDataSource.getTvDetail(id)
        }.asFlowable()
    }

    override fun getTvReviews(id: String): Flowable<Resource<List<Review>>> {
        return object : NetworkBoundResource<List<Review>, ReviewResponse>() {
            override fun createResult(data: ReviewResponse) = data.map()
            override fun createCall() = remoteDataSource.getReviewList(TV, id, PAGE)
        }.asFlowable()
    }

    override fun getTvWallpapers(id: String): Flowable<Resource<Wallpaper>> {
        return object : NetworkBoundResource<Wallpaper, WallpaperResponse>() {
            override fun createResult(data: WallpaperResponse) = data.map()
            override fun createCall() = remoteDataSource.getWallpaperList(TV, id)
        }.asFlowable()
    }

    override fun getTvActors(id: String, region: String): Flowable<Resource<List<Actor>>> {
        return object : NetworkBoundResource<List<Actor>, ActorResponse>() {
            override fun createResult(data: ActorResponse) = data.map()
            override fun createCall() = remoteDataSource.getCreditList(TV, id, region)
        }.asFlowable()
    }

    override fun getTvVideos(id: String): Flowable<Resource<List<Video>>> {
        return object : NetworkBoundResource<List<Video>, VideoResponse>() {
            override fun createResult(data: VideoResponse) = data.map()
            override fun createCall() = remoteDataSource.getVideoList(MovieDetailRepositoryImpl.MOVIE, id)
        }.asFlowable()
    }

    override fun getRecommendationsTv(id: String, region: String): Flowable<Resource<TvResult>> {
        return object : NetworkBoundResource<TvResult, TvResponse>() {
            override fun createResult(data: TvResponse) = data.map()
            override fun createCall() = remoteDataSource.getRecommendationsTv(id, PAGE, region)
        }.asFlowable()
    }

    override fun getSimilarTv(id: String, region: String): Flowable<Resource<TvResult>> {
        return object : NetworkBoundResource<TvResult, TvResponse>() {
            override fun createResult(data: TvResponse) = data.map()
            override fun createCall() = remoteDataSource.getSimilarTv(id, PAGE, region)
        }.asFlowable()
    }

    companion object {
        const val PAGE = "1"
        const val TV = "tv"
    }
}