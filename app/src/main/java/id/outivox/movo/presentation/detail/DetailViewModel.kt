package id.outivox.movo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.Resource.Companion.init
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.usecase.detail.movie.MovieDetailUseCase
import id.outivox.core.domain.usecase.detail.tv.TvDetailUseCase
import id.outivox.core.utils.Constants.INDONESIA
import kotlinx.coroutines.launch

class DetailViewModel(private val movieDetailUseCase: MovieDetailUseCase, private val tvDetailUseCase: TvDetailUseCase) : ViewModel() {
    // Movie
    private val _movieDetail = MutableLiveData<Resource<MovieDetail>>()
    val movieDetail get() = _movieDetail

    private val _movieSimilar = MutableLiveData<Resource<PagingData<Movie>>>()
    val movieSimilar get() = _movieSimilar

    private val _movieRecommendations = MutableLiveData<Resource<PagingData<Movie>>>()
    val movieRecommendations get() = _movieRecommendations

    // TV
    private val _tvDetail = MutableLiveData<Resource<TvDetail>>()
    val tvDetail get() = _tvDetail

    private val _tvSimilar = MutableLiveData<Resource<PagingData<Tv>>>()
    val tvSimilar get() = _tvSimilar

    private val _tvRecommendations = MutableLiveData<Resource<PagingData<Tv>>>()
    val tvRecommendations get() = _tvRecommendations

    // Details
    private val _actor = MutableLiveData<Resource<List<Actor>>>()
    val actor get() = _actor

    private val _review = MutableLiveData<Resource<PagingData<Review>>>()
    val review get() = _review

    private val _wallpaper = MutableLiveData<Resource<Wallpaper>>()
    val wallpaper get() = _wallpaper

    private val _video = MutableLiveData<Resource<List<Video>>>()
    val video get() = _video

    init {
        _movieDetail.value = init()
        _movieSimilar.value = init()
        _movieRecommendations.value = init()

        _tvDetail.value = init()
        _tvSimilar.value = init()
        _tvRecommendations.value = init()

        _actor.value = init()
        _review.value = init()
        _wallpaper.value = init()
        _video.value = init()
    }

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            _movieDetail.value = loading()
            movieDetailUseCase.getMovieDetail(id).collect {
                _movieDetail.value = it
            }
        }
    }

    fun getMovieActor(id: Int) {
        viewModelScope.launch {
            _actor.value = loading()
            movieDetailUseCase.getMovieActors(id).collect {
                _actor.value = it
            }
        }
    }

    fun getMovieWallpaper(id: Int) {
        viewModelScope.launch {
            _wallpaper.value = loading()
            movieDetailUseCase.getMovieWallpapers(id).collect {
                _wallpaper.value = it
            }
        }
    }

    fun getMovieTrailer(id: Int) {
        viewModelScope.launch {
            _video.value = loading()
            movieDetailUseCase.getMovieVideos(id).collect {
                _video.value = it
            }
        }
    }

    fun getMovieReviews(id: Int) {
        viewModelScope.launch {
            _review.value = loading()
            movieDetailUseCase.getMovieReviews(id).collect {
                _review.value = it
            }
        }
    }

    fun getRecommendationsMovies(id: Int) {
        viewModelScope.launch {
            _movieRecommendations.value = loading()
            movieDetailUseCase.getRecommendationsMovies(id).collect {
                _movieRecommendations.value = it
            }
        }
    }

    fun getSimilarMovies(id: Int) {
        viewModelScope.launch {
            _movieSimilar.value = loading()
            movieDetailUseCase.getSimilarMovies(id).collect {
                _movieSimilar.value = it
            }
        }
    }

    fun getTvDetail(id: Int) {
        viewModelScope.launch {
            _tvDetail.value = loading()
            tvDetailUseCase.getTvDetail(id).collect {
                _tvDetail.value = it
            }
        }
    }

    fun getTvActor(id: Int) {
        viewModelScope.launch {
            _actor.value = loading()
            tvDetailUseCase.getTvActors(id).collect {
                _actor.value = it
            }
        }
    }

    fun getTvWallpaper(id: Int) {
        viewModelScope.launch {
            _wallpaper.value = loading()
            tvDetailUseCase.getTvWallpapers(id).collect {
                _wallpaper.value = it
            }
        }
    }

    fun getTvTrailer(id: Int) {
        viewModelScope.launch {
            _video.value = loading()
            tvDetailUseCase.getTvVideos(id).collect {
                _video.value = it
            }
        }
    }

    fun getTvReviews(id: Int) {
        viewModelScope.launch {
            _review.value = loading()
            tvDetailUseCase.getTvReviews(id).collect {
                _review.value = it
            }
        }
    }

    fun getRecommendationsTv(id: Int) {
        viewModelScope.launch {
            _tvRecommendations.value = loading()
            tvDetailUseCase.getRecommendationsTv(id).collect {
                _tvRecommendations.value = it
            }
        }
    }

    fun getSimilarTv(id: Int) {
        viewModelScope.launch {
            _tvSimilar.value = loading()
            tvDetailUseCase.getSimilarTv(id).collect {
                _tvSimilar.value = it
            }
        }
    }
}