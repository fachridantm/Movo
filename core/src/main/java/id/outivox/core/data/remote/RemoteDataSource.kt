package id.outivox.core.data.remote

import id.outivox.core.data.remote.source.network.ApiService
import id.outivox.core.data.remote.source.response.detail.actor.ActorResponse
import id.outivox.core.data.remote.source.response.detail.movie.MovieDetailResponse
import id.outivox.core.data.remote.source.response.detail.review.ReviewResponse
import id.outivox.core.data.remote.source.response.detail.tv.TvDetailResponse
import id.outivox.core.data.remote.source.response.detail.video.VideoResponse
import id.outivox.core.data.remote.source.response.detail.wallpaper.WallpaperResponse
import id.outivox.core.data.remote.source.response.movie.MovieResponse
import id.outivox.core.data.remote.source.response.tv.TvResponse
import id.outivox.movo.core.BuildConfig.API_KEY
import id.outivox.movo.core.BuildConfig.REGION
import io.reactivex.rxjava3.core.Flowable

class RemoteDataSource(
    private val apiService: ApiService
) {
    // Movie
    fun getMovieDetail(id: String) = apiService.getMovieDetail(id, apiKey)
    fun getSimilarMovies(id: String, page: String) = apiService.getSimilarMovies(id, apiKey, region, page)
    fun getRecommendationsMovies(id: String, page: String) = apiService.getRecommendationsMovies(id, apiKey, region, page)
    fun getMoviesByCategory(category: String, page: String) = apiService.getMovieByCategory(category, apiKey, region, page)
    fun searchMovieByQuery(query: String, page: String) = apiService.searchMovieByQuery(apiKey, query, region, page)

    // TV Show
    fun getTvDetail(id: String) = apiService.getTvDetail(id, apiKey)
    fun getTvByCategory(category: String, page: String) = apiService.getTvByCategory(category, apiKey, region, page)
    fun searchTvByQuery(query: String, page: String) = apiService.searchTvByQuery(apiKey, query, region, page)
    fun getSimilarTv(id: String, page: String) = apiService.getSimilarTv(id, apiKey, region, page)
    fun getRecommendationsTv(id: String, page: String) = apiService.getRecommendationsTv(id, apiKey, region, page)

    // Detail
    fun getReviewList(media: String, id: String, page: String) = apiService.getReviewList(media, id, apiKey, page)
    fun getCreditList(media: String, id: String, ) = apiService.getCreditList(media, id, apiKey, region)
    fun getVideoList(media: String, id: String, ) = apiService.getVideoList(media, id, apiKey)
    fun getWallpaperList(media: String, id: String, ) = apiService.getWallpaperList(media, id, apiKey)

    companion object {
        const val apiKey = API_KEY
        const val region = REGION
    }
}