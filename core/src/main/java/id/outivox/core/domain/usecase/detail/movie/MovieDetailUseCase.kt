package id.outivox.core.domain.usecase.detail.movie

import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.*
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface MovieDetailUseCase {
    fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>
    fun getMovieReviews(id: Int): Flow<Resource<PagingData<Review>>>
    fun getSimilarMovies(id: Int): Flow<Resource<PagingData<Movie>>>
    fun getRecommendationsMovies(id: Int): Flow<Resource<PagingData<Movie>>>
    fun getMovieWallpapers(id: Int): Flow<Resource<Wallpaper>>
    fun getMovieActors(id: Int): Flow<Resource<List<Actor>>>
    fun getMovieVideos(id: Int): Flow<Resource<List<Video>>>
}