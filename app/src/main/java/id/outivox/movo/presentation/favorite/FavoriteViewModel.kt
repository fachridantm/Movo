package id.outivox.movo.presentation.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.usecase.favorite.FavoriteUseCase
import id.outivox.core.utils.Event
import id.outivox.movo.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    private val _snackbar = MutableLiveData<Event<String>>()
    val snackbar: LiveData<Event<String>> = _snackbar

    val favoriteListMovies = MediatorLiveData<List<MovieDetail>>()
    val favoriteListTv = MediatorLiveData<List<TvDetail>>()

    fun getFavoriteMovies() {
        val source = favoriteUseCase.getFavoriteMovies().asLiveData()

        favoriteListMovies.addSource(source) {
            favoriteListMovies.postValue(it)
            favoriteListMovies.removeSource(source)
        }
    }

    fun getFavoriteTv() {
        val source = favoriteUseCase.getFavoriteTv().asLiveData()

        favoriteListTv.addSource(source) {
            favoriteListTv.postValue(it)
            favoriteListTv.removeSource(source)
        }
    }

    fun setFavoriteMovie(movie: MovieDetail, isFavorite: Boolean, context: Context): Job {
        return viewModelScope.launch {
            favoriteUseCase.setFavoriteMovie(movie, isFavorite)
            Event(
                if (isFavorite) context.getString(R.string.added_to_favorite, movie.title)
                else context.getString(R.string.removed_from_favorite, movie.title)
            ).also { _snackbar.value = it }
        }
    }

    fun setFavoriteTv(tv: TvDetail, isFavorite: Boolean, context: Context): Job {
        return viewModelScope.launch {
            favoriteUseCase.setFavoriteTv(tv, isFavorite)
            Event(
                if (isFavorite) context.getString(R.string.added_to_favorite, tv.title)
                else context.getString(R.string.removed_from_favorite, tv.title)
            ).also { _snackbar.value = it }
        }
    }
}