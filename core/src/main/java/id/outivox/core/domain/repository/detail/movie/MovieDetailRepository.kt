package id.outivox.core.domain.repository.detail.movie

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>
    fun getMovieReviews(id: Int): Flow<Resource<PagingData<Review>>>
    fun getSimilarMovies(id: Int): Flow<Resource<PagingData<Movie>>>
    fun getRecommendationsMovies(id: Int): Flow<Resource<PagingData<Movie>>>
    fun getMovieWallpapers(id: Int): Flow<Resource<Wallpaper>>
    fun getMovieActors(id: Int): Flow<Resource<List<Actor>>>
    fun getMovieVideos(id: Int): Flow<Resource<List<Video>>>
}