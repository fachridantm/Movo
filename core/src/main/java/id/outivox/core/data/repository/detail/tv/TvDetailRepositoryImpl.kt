package id.outivox.core.data.repository.detail.tv

import android.util.Log
import androidx.paging.map
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.network.ApiResponse
import id.outivox.core.domain.model.Resource.Companion.empty
import id.outivox.core.domain.model.Resource.Companion.error
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.Resource.Companion.success
import id.outivox.core.domain.repository.detail.tv.TvDetailRepository
import id.outivox.core.mapper.DetailsMapper.map
import id.outivox.core.mapper.TvMapper.map
import id.outivox.core.utils.Constants.TV_SHOW
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class TvDetailRepositoryImpl(private val remoteDataSource: RemoteDataSource) : TvDetailRepository {
    override fun getTvDetail(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getTvDetail(id).first()) {
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

    override fun getSimilarTv(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getSimilarTv(id).first()) {
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

    override fun getTvReviews(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getReviewList(TV_SHOW, id).first()) {
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

    override fun getRecommendationsTv(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getRecommendationsTv(id).first()) {
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

    override fun getTvWallpapers(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getWallpaperList(TV_SHOW, id).first()) {
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

    override fun getTvActors(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getCreditList(TV_SHOW, id).first()) {
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

    override fun getTvVideos(id: Int) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getVideoList(TV_SHOW, id).first()) {
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