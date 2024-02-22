package id.outivox.core.domain.usecase.detail.movie

import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.*
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.flow.Flow

interface MovieDetailUseCase {
    fun getMovieDetail(id: String): Flowable<Resource<MovieDetail>>
    fun getMovieReviews(id: String): Flowable<Resource<List<Review>>>
    fun getMovieWallpapers(id: String): Flowable<Resource<Wallpaper>>
    fun getMovieActors(id: String): Flowable<Resource<List<Actor>>>
    fun getMovieVideos(id: String): Flowable<Resource<List<Video>>>

    fun isFollowed(id: String): Flow<Boolean>

    fun insertFavoriteMovie(movie: Movie)
    fun deleteFavoriteMovie(movie: Movie)

    fun getRecommendationsMovies(id: String): Flowable<Resource<MovieResult>>
    fun getSimilarMovies(id: String): Flowable<Resource<MovieResult>>

}