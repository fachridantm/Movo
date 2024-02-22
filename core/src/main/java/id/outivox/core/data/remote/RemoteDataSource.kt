package id.outivox.core.data.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import id.outivox.core.data.remote.paging.details.GetReviewList
import id.outivox.core.data.remote.paging.movie.GetMoviesByCategory
import id.outivox.core.data.remote.paging.movie.GetRecommendationsMovies
import id.outivox.core.data.remote.paging.movie.SearchMovieByCategory
import id.outivox.core.data.remote.paging.tv.GetRecommendationsTv
import id.outivox.core.data.remote.paging.tv.GetSimilarTv
import id.outivox.core.data.remote.paging.tv.GetTvByCategory
import id.outivox.core.data.remote.paging.tv.SearchTvByCategory
import id.outivox.core.data.remote.source.network.ApiResponse.Companion.apiError
import id.outivox.core.data.remote.source.network.ApiResponse.Companion.apiSuccess
import id.outivox.core.data.remote.source.network.ApiService
import id.outivox.core.utils.localizedApiCodeErrorMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteDataSource(private val apiService: ApiService) {
    // Movie
    suspend fun getMovieDetail(id: Int) = flow {
        try {
            val response = apiService.getMovieDetail(id)
            emit(apiSuccess(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    emit(apiError(e.localizedApiCodeErrorMessage()))
                    Log.e("logError", e.localizedApiCodeErrorMessage())
                }

                is UnknownHostException -> emit(apiError("No internet connection"))

                is SocketTimeoutException -> emit(apiError("Connection timeout"))

                else -> {
                    emit(apiError("Something went wrong"))
                    Log.e("logError", e.message.orEmpty())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSimilarMovies(id: Int) = flow {
        try {
            val response = apiService.getSimilarMovies(id)
            emit(apiSuccess(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    emit(apiError(e.localizedApiCodeErrorMessage()))
                    Log.e("logError", e.localizedApiCodeErrorMessage())
                }

                is UnknownHostException -> emit(apiError("No internet connection"))

                is SocketTimeoutException -> emit(apiError("Connection timeout"))

                else -> {
                    emit(apiError("Something went wrong"))
                    Log.e("logError", e.message.orEmpty())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getRecommendationsMovies(id: Int) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { GetRecommendationsMovies(apiService, id) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun getMoviesByCategory(category: String, region: String) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { GetMoviesByCategory(apiService, category, region) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun searchMovieByQuery(query: String, region: String) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { SearchMovieByCategory(apiService, query, region) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    // TV Show
    suspend fun getTvDetail(id: Int) = flow {
        try {
            val response = apiService.getTvDetail(id)
            emit(apiSuccess(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    emit(apiError(e.localizedApiCodeErrorMessage()))
                    Log.e("logError", e.localizedApiCodeErrorMessage())
                }

                is UnknownHostException -> emit(apiError("No internet connection"))

                is SocketTimeoutException -> emit(apiError("Connection timeout"))

                else -> {
                    emit(apiError("Something went wrong"))
                    Log.e("logError", e.message.orEmpty())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTvByCategory(category: String) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { GetTvByCategory(apiService, category) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun searchTvByQuery(query: String) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { SearchTvByCategory(apiService, query) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun getSimilarTv(id: Int) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { GetSimilarTv(apiService, id) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun getRecommendationsTv(id: Int) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { GetRecommendationsTv(apiService, id) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    // Details
    suspend fun getReviewList(media: String, id: Int) = flow {
        Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { GetReviewList(apiService, media, id) }
        ).flow
            .map { apiSuccess(it) }
            .catch { e ->
                when (e) {
                    is HttpException -> {
                        emit(apiError(e.localizedApiCodeErrorMessage()))
                        Log.e("logError", e.localizedApiCodeErrorMessage())
                    }

                    is UnknownHostException -> emit(apiError("No internet connection"))

                    is SocketTimeoutException -> emit(apiError("Connection timeout"))

                    else -> {
                        emit(apiError("Something went wrong"))
                        Log.e("logError", e.message.orEmpty())
                    }
                }
            }.flowOn(Dispatchers.IO).collect { emit(it) }
    }.flowOn(Dispatchers.IO)

    suspend fun getCreditList(media: String, id: Int) = flow {
        try {
            val response = apiService.getCreditList(media, id)
            emit(apiSuccess(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    emit(apiError(e.localizedApiCodeErrorMessage()))
                    Log.e("logError", e.localizedApiCodeErrorMessage())
                }

                is UnknownHostException -> emit(apiError("No internet connection"))

                is SocketTimeoutException -> emit(apiError("Connection timeout"))

                else -> {
                    emit(apiError("Something went wrong"))
                    Log.e("logError", e.message.orEmpty())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getVideoList(media: String, id: Int) = flow {
        try {
            val response = apiService.getVideoList(media, id)
            emit(apiSuccess(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    emit(apiError(e.localizedApiCodeErrorMessage()))
                    Log.e("logError", e.localizedApiCodeErrorMessage())
                }

                is UnknownHostException -> emit(apiError("No internet connection"))

                is SocketTimeoutException -> emit(apiError("Connection timeout"))

                else -> {
                    emit(apiError("Something went wrong"))
                    Log.e("logError", e.message.orEmpty())
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getWallpaperList(media: String, id: Int) = flow {
        try {
            val response = apiService.getWallpaperList(media, id)
            emit(apiSuccess(response))
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    emit(apiError(e.localizedApiCodeErrorMessage()))
                    Log.e("logError", e.localizedApiCodeErrorMessage())
                }

                is UnknownHostException -> emit(apiError("No internet connection"))

                is SocketTimeoutException -> emit(apiError("Connection timeout"))

                else -> {
                    emit(apiError("Something went wrong"))
                    Log.e("logError", e.message.orEmpty())
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}