package id.outivox.core.domain.usecase.detail.movie

import id.outivox.core.domain.repository.detail.movie.MovieDetailRepository

class MovieDetailInteractor(private val movieDetailRepository: MovieDetailRepository): MovieDetailUseCase {
    override fun getMovieDetail(id: Int) = movieDetailRepository.getMovieDetail(id)
    override fun getSimilarMovies(id: Int) = movieDetailRepository.getSimilarMovies(id)
    override fun getMovieReviews(id: Int) = movieDetailRepository.getMovieReviews(id)
    override fun getRecommendationsMovies(id: Int) = movieDetailRepository.getRecommendationsMovies(id)
    override fun getMovieWallpapers(id: Int) = movieDetailRepository.getMovieWallpapers(id)
    override fun getMovieActors(id: Int) = movieDetailRepository.getMovieActors(id)
    override fun getMovieVideos(id: Int) = movieDetailRepository.getMovieVideos(id)
}