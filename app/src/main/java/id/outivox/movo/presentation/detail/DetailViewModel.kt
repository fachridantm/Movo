package id.outivox.movo.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.Resource.Companion.init
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.Resource.Companion.success
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
import id.outivox.core.utils.Constants.INDONESIA
import kotlinx.coroutines.launch

class DetailViewModel(private val movieDetailUseCase: MovieDetailUseCase, private val tvDetailUseCase: TvDetailUseCase) : ViewModel() {
    // Movie
    private val _movieDetail = MutableLiveData<Resource<MovieDetail>>()
    val movieDetail: LiveData<Resource<MovieDetail>> get() = _movieDetail

    private val _movieSimilar = MutableLiveData<Resource<MovieResult>>()
    val movieSimilar: LiveData<Resource<MovieResult>> get() = _movieSimilar

    private val _movieRecommendations = MutableLiveData<Resource<MovieResult>>()
    val movieRecommendations: LiveData<Resource<MovieResult>> get() = _movieRecommendations

    // TV
    private val _tvDetail = MutableLiveData<Resource<TvDetail>>()
    val tvDetail: LiveData<Resource<TvDetail>> get() = _tvDetail

    private val _tvSimilar = MutableLiveData<Resource<TvResult>>()
    val tvSimilar: LiveData<Resource<TvResult>> get() = _tvSimilar

    private val _tvRecommendations = MutableLiveData<Resource<TvResult>>()
    val tvRecommendations: LiveData<Resource<TvResult>> get() = _tvRecommendations

    // Details
    private val _actor = MutableLiveData<Resource<List<Actor>>>()
    val actor: LiveData<Resource<List<Actor>>> get() = _actor

    private val _review = MutableLiveData<Resource<List<Review>>>()
    val review: LiveData<Resource<List<Review>>> get() = _review

    private val _wallpaper = MutableLiveData<Resource<Wallpaper>>()
    val wallpaper: LiveData<Resource<Wallpaper>> get() = _wallpaper

    private val _video = MutableLiveData<Resource<List<Video>>>()
    val video: LiveData<Resource<List<Video>>> get() = _video

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

    fun getMovieDetail(id: String) {
        viewModelScope.launch {
            _movieDetail.value = loading()
            movieDetailUseCase.getMovieDetail(id).collect {
                _movieDetail.value = it
            }
        }
    }

    fun getMovieActor(id: String) {
        viewModelScope.launch {
            _actor.value = loading()
            movieDetailUseCase.getMovieActors(id, INDONESIA).collect {
                _actor.value = it
            }
        }
    }

    fun getMovieWallpaper(id: String) {
        viewModelScope.launch {
            _wallpaper.value = loading()
            movieDetailUseCase.getMovieWallpapers(id).collect {
                _wallpaper.value = it
            }
        }
    }

    fun getMovieTrailer(id: String) {
        viewModelScope.launch {
            _video.value = loading()
            movieDetailUseCase.getMovieVideos(id).collect {
                _video.value = it
            }
        }
    }

    fun getMovieReviews(id: String) {
        viewModelScope.launch {
            _review.value = loading()
            movieDetailUseCase.getMovieReviews(id).collect {
                _review.value = it
            }
        }
    }

    fun getRecommendationsMovies(id: String) {
        viewModelScope.launch {
            _movieRecommendations.value = loading()
            movieDetailUseCase.getRecommendationsMovies(id, INDONESIA).collect {
                _movieRecommendations.value = it
            }
        }
    }

    fun getSimilarMovies(id: String) {
        viewModelScope.launch {
            _movieSimilar.value = loading()
            movieDetailUseCase.getSimilarMovies(id, INDONESIA).collect {
                _movieSimilar.value = it
            }
        }
    }

    fun getTvDetail(id: String) {
        viewModelScope.launch {
            _tvDetail.value = loading()
            tvDetailUseCase.getTvDetail(id).collect {
                _tvDetail.value = it
            }
        }
    }

    fun getTvActor(id: String) {
        viewModelScope.launch {
            _actor.value = loading()
            tvDetailUseCase.getTvActors(id, INDONESIA).collect {
                _actor.value = it
            }
        }
    }

    fun getTvWallpaper(id: String) {
        viewModelScope.launch {
            _wallpaper.value = loading()
            tvDetailUseCase.getTvWallpapers(id).collect {
                _wallpaper.value = it
            }
        }
    }

    fun getTvTrailer(id: String) {
        viewModelScope.launch {
            _video.value = loading()
            tvDetailUseCase.getTvVideos(id).collect {
                _video.value = it
            }
        }
    }

    fun getTvReviews(id: String) {
        viewModelScope.launch {
            _review.value = loading()
            tvDetailUseCase.getTvReviews(id).collect {
                _review.value = it
            }
        }
    }

    fun getRecommendationsTv(id: String) {
        viewModelScope.launch {
            _tvRecommendations.value = loading()
            tvDetailUseCase.getRecommendationsTv(id, INDONESIA).collect {
                _tvRecommendations.value = it
            }
        }
    }

    fun getSimilarTv(id: String) {
        viewModelScope.launch {
            _tvSimilar.value = loading()
            tvDetailUseCase.getSimilarTv(id, INDONESIA).collect {
                _tvSimilar.value = it
            }
        }
    }
}