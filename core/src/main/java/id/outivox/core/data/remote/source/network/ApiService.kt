package id.outivox.core.data.remote.source.network

import id.outivox.core.data.remote.source.response.*
import id.outivox.core.data.remote.source.response.detail.actor.ActorResponse
import id.outivox.core.data.remote.source.response.detail.movie.MovieDetailResponse
import id.outivox.core.data.remote.source.response.detail.review.ReviewResponse
import id.outivox.core.data.remote.source.response.detail.tv.TvDetailResponse
import id.outivox.core.data.remote.source.response.detail.video.VideoResponse
import id.outivox.core.data.remote.source.response.detail.wallpaper.WallpaperResponse
import id.outivox.core.data.remote.source.response.movie.MovieResponse
import id.outivox.core.data.remote.source.response.tv.TvResponse
import id.outivox.core.utils.Constants.ENGLISH
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Movie
    @GET("movie/{category}")
    suspend fun getMoviesByCategory(
        @Path("category") category: String,
        @Query("region") region: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH,
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int // required
    ): MovieDetailResponse

    @GET("search/movie")
    suspend fun searchMovieByQuery(
        @Query("query") query: String, // required
        @Query("region") region: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH,
        @Query("include_adult") adult: Boolean = false,
        @Query("year") year: String? = null
    ): MovieResponse

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") id: Int, // required
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH
    ): MovieResponse

    @GET("movie/{id}/recommendations")
    suspend fun getRecommendationsMovies(
        @Path("id") id: Int, // required
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH
    ): MovieResponse

    // TV Show
    @GET("tv/{category}")
    suspend fun getTvByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH
    ): TvResponse

    @GET("tv/{id}")
    suspend fun getTvDetail(
        @Path("id") id: Int, // required
        @Query("language") language: String = ENGLISH
    ): TvDetailResponse

    @GET("search/tv")
    suspend fun searchTvByQuery(
        @Query("query") query: String, // required
        @Query("include_adult") adult: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH,
        @Query("year") year: String? = null
    ): TvResponse

    @GET("tv/{id}/similar")
    suspend fun getSimilarTv(
        @Path("id") id: Int, // required
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH
    ): TvResponse

    @GET("tv/{id}/recommendations")
    suspend fun getRecommendationsTv(
        @Path("id") id: Int, // required
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH
    ): TvResponse

    // Details
    @GET("{media}/{id}/reviews")
    suspend fun getReviewList(
        @Path("media") media: String,
        @Path("id") id: Int,
        @Query("page") page: Int = 1,
        @Query("language") language: String = ENGLISH
    ): ReviewResponse

    @GET("{media}/{id}/credits")
    suspend fun getCreditList(
        @Path("media") media: String,
        @Path("id") id: Int,
        @Query("language") language: String = ENGLISH
    ): ActorResponse

    @GET("{media}/{id}/videos")
    suspend fun getVideoList(
        @Path("media") media: String,
        @Path("id") id: Int,
        @Query("language") language: String = ENGLISH
    ): VideoResponse

    @GET("{media}/{id}/images")
    suspend fun getWallpaperList(
        @Path("media") media: String,
        @Path("id") id: Int,
        @Query("language") language: String = ENGLISH,
    ): WallpaperResponse
}