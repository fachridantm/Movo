package id.outivox.core.data.repository.detail.movie

import android.util.Log
import androidx.paging.map
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.network.ApiResponse
import id.outivox.core.domain.model.Resource.Companion.empty
import id.outivox.core.domain.model.Resource.Companion.error
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.Resource.Companion.success
import id.outivox.core.domain.repository.detail.movie.MovieDetailRepository
import id.outivox.core.mapper.DetailsMapper.map
import id.outivox.core.mapper.MovieMapper.map
import id.outivox.core.utils.Constants.MOVIE
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MovieDetailRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MovieDetailRepository {
    override fun getMovieDetail(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getMovieDetail(id).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map()
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> {
                    if (e.message == "Index: 0, Size: 0") emit(empty())
                    else emit(error(e.message.orEmpty()))
                }

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    emit(error(e.message.orEmpty()))
                }
            }
        }
    }.catch { e ->
        when (e) {
            is IndexOutOfBoundsException -> {
                if (e.message == "Index: 0, Size: 0") emit(empty())
                else emit(error(e.message.orEmpty()))
            }

            else -> {
                Log.e("logError", e.message.orEmpty())
                emit(error(e.message.orEmpty()))
            }
        }
    }

    override fun getSimilarMovies(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getSimilarMovies(id).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> {
                    if (e.message == "Index: 0, Size: 0") emit(empty())
                    else emit(error(e.message.orEmpty()))
                }

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    emit(error(e.message.orEmpty()))
                }
            }
        }
    }.catch { e ->
        when (e) {
            is IndexOutOfBoundsException -> {
                if (e.message == "Index: 0, Size: 0") emit(empty())
                else emit(error(e.message.orEmpty()))
            }

            else -> {
                Log.e("logError", e.message.orEmpty())
                emit(error(e.message.orEmpty()))
            }
        }
    }

    override fun getMovieReviews(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getReviewList(MOVIE, id).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> {
                    if (e.message == "Index: 0, Size: 0") emit(empty())
                    else emit(error(e.message.orEmpty()))
                }

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    emit(error(e.message.orEmpty()))
                }
            }
        }
    }.catch { e ->
        when (e) {
            is IndexOutOfBoundsException -> {
                if (e.message == "Index: 0, Size: 0") emit(empty())
                else emit(error(e.message.orEmpty()))
            }

            else -> {
                Log.e("logError", e.message.orEmpty())
                emit(error(e.message.orEmpty()))
            }
        }
    }

    override fun getRecommendationsMovies(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getRecommendationsMovies(id).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> {
                    if (e.message == "Index: 0, Size: 0") emit(empty())
                    else emit(error(e.message.orEmpty()))
                }

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    emit(error(e.message.orEmpty()))
                }
            }
        }
    }.catch { e ->
        when (e) {
            is IndexOutOfBoundsException -> {
                if (e.message == "Index: 0, Size: 0") emit(empty())
                else emit(error(e.message.orEmpty()))
            }

            else -> {
                Log.e("logError", e.message.orEmpty())
                emit(error(e.message.orEmpty()))
            }
        }
    }

    override fun getMovieWallpapers(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getWallpaperList(MOVIE, id).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map()
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> {
                    if (e.message == "Index: 0, Size: 0") emit(empty())
                    else emit(error(e.message.orEmpty()))
                }

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    emit(error(e.message.orEmpty()))
                }
            }
        }
    }.catch { e ->
        when (e) {
            is IndexOutOfBoundsException -> {
                if (e.message == "Index: 0, Size: 0") emit(empty())
                else emit(error(e.message.orEmpty()))
            }

            else -> {
                Log.e("logError", e.message.orEmpty())
                emit(error(e.message.orEmpty()))
            }
        }
    }

    override fun getMovieActors(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getCreditList(MOVIE, id).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map()
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> {
                    if (e.message == "Index: 0, Size: 0") emit(empty())
                    else emit(error(e.message.orEmpty()))
                }

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    emit(error(e.message.orEmpty()))
                }
            }
        }
    }.catch { e ->
        when (e) {
            is IndexOutOfBoundsException -> {
                if (e.message == "Index: 0, Size: 0") emit(empty())
                else emit(error(e.message.orEmpty()))
            }

            else -> {
                Log.e("logError", e.message.orEmpty())
                emit(error(e.message.orEmpty()))
            }
        }
    }

    override fun getMovieVideos(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getVideoList(MOVIE, id).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map()
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> {
                    if (e.message == "Index: 0, Size: 0") emit(empty())
                    else emit(error(e.message.orEmpty()))
                }

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    emit(error(e.message.orEmpty()))
                }
            }
        }
    }.catch { e ->
        when (e) {
            is IndexOutOfBoundsException -> {
                if (e.message == "Index: 0, Size: 0") emit(empty())
                else emit(error(e.message.orEmpty()))
            }

            else -> {
                Log.e("logError", e.message.orEmpty())
                emit(error(e.message.orEmpty()))
            }
        }
    }
}