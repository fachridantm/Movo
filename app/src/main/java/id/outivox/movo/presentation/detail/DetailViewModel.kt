package id.outivox.movo.presentation.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.usecase.detail.movie.MovieDetailUseCase
import id.outivox.core.domain.usecase.detail.tv.TvDetailUseCase

class DetailViewModel(private val movieDetailUseCase: MovieDetailUseCase, private val tvDetailUseCase: TvDetailUseCase) : ViewModel() {

    val movieDetailResponse = MediatorLiveData<Resource<MovieDetail>>()
    val movieSimilarResponse = MediatorLiveData<Resource<MovieResult>>()
    val movieRecommendationsResponse = MediatorLiveData<Resource<MovieResult>>()

    val tvDetailResponse = MediatorLiveData<Resource<TvDetail>>()
    val tvSimilarResponse = MediatorLiveData<Resource<TvResult>>()
    val tvRecommendationsResponse = MediatorLiveData<Resource<TvResult>>()

    val actorResponse = MediatorLiveData<Resource<List<Actor>>>()
    val reviewResponse = MediatorLiveData<Resource<List<Review>>>()
    val wallpaperResponse = MediatorLiveData<Resource<Wallpaper>>()
    val videoResponse = MediatorLiveData<Resource<List<Video>>>()

    fun getMovieDetail(id: String) {
        val source = movieDetailUseCase.getMovieDetail(id).toLiveData()

        movieDetailResponse.addSource(source) {
            movieDetailResponse.postValue(it)
            movieDetailResponse.removeSource(source)
        }
    }

    fun getMovieActor(id: String) {
        val source = movieDetailUseCase.getMovieActors(id).toLiveData()

        actorResponse.addSource(source) {
            actorResponse.postValue(it)
            actorResponse.removeSource(source)
        }
    }

    fun getMovieWallpaper(id: String) {
        val source = movieDetailUseCase.getMovieWallpapers(id).toLiveData()

        wallpaperResponse.addSource(source) {
            wallpaperResponse.postValue(it)
            wallpaperResponse.removeSource(source)
        }
    }

    fun getMovieTrailer(id: String) {
        val source = movieDetailUseCase.getMovieVideos(id).toLiveData()

        videoResponse.addSource(source) {
            videoResponse.postValue(it)
            videoResponse.removeSource(source)
        }
    }

    fun getMovieReviews(id: String) {
        val source = movieDetailUseCase.getMovieReviews(id).toLiveData()

        reviewResponse.addSource(source) {
            reviewResponse.postValue(it)
            reviewResponse.removeSource(source)
        }
    }

    fun getRecommendationsMovies(id: String) {
        val source = movieDetailUseCase.getRecommendationsMovies(id).toLiveData()

        movieRecommendationsResponse.addSource(source) {
            movieRecommendationsResponse.postValue(it)
            movieRecommendationsResponse.removeSource(source)
        }
    }

    fun getSimilarMovies(id: String) {
        val source = movieDetailUseCase.getSimilarMovies(id).toLiveData()

        movieSimilarResponse.addSource(source) {
            movieSimilarResponse.postValue(it)
            movieSimilarResponse.removeSource(source)
        }
    }

    fun getTvDetail(id: String) {
        val source = tvDetailUseCase.getTvDetail(id).toLiveData()

        tvDetailResponse.addSource(source) {
            tvDetailResponse.postValue(it)
            tvDetailResponse.removeSource(source)
        }
    }

    fun getTvActor(id: String) {
        val source = movieDetailUseCase.getMovieActors(id).toLiveData()

        actorResponse.addSource(source) {
            actorResponse.postValue(it)
            actorResponse.removeSource(source)
        }
    }

    fun getTvWallpaper(id: String) {
        val source = movieDetailUseCase.getMovieWallpapers(id).toLiveData()

        wallpaperResponse.addSource(source) {
            wallpaperResponse.postValue(it)
            wallpaperResponse.removeSource(source)
        }
    }

    fun getTvTrailer(id: String) {
        val source = movieDetailUseCase.getMovieVideos(id).toLiveData()

        videoResponse.addSource(source) {
            videoResponse.postValue(it)
            videoResponse.removeSource(source)
        }
    }

    fun getTvReviews(id: String) {
        val source = movieDetailUseCase.getMovieReviews(id).toLiveData()

        reviewResponse.addSource(source) {
            reviewResponse.postValue(it)
            reviewResponse.removeSource(source)
        }
    }

    fun getRecommendationsTv(id: String) {
        val source = tvDetailUseCase.getRecommendationsTv(id).toLiveData()

        tvRecommendationsResponse.addSource(source) {
            tvRecommendationsResponse.postValue(it)
            tvRecommendationsResponse.removeSource(source)
        }
    }

    fun getSimilarTv(id: String) {
        val source = tvDetailUseCase.getSimilarTv(id).toLiveData()

        tvSimilarResponse.addSource(source) {
            tvSimilarResponse.postValue(it)
            tvSimilarResponse.removeSource(source)
        }
    }

}