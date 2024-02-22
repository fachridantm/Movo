package id.outivox.core.data.repository.allmovie

import android.util.Log
import androidx.paging.map
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.network.ApiResponse
import id.outivox.core.domain.model.Resource.Companion.empty
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.Resource.Companion.success
import id.outivox.core.domain.model.Resource.Companion.error
import id.outivox.core.domain.repository.allmovietv.AllMovieTvRepository
import id.outivox.core.mapper.MovieMapper.map
import id.outivox.core.mapper.TvMapper.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AllMovieTvRepositoryImpl(val remoteDataSource: RemoteDataSource) : AllMovieTvRepository {
    override fun getMovies(category: String, region: String) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getMoviesByCategory(category, region).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(success(data))
                }
                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            Log.e("logError", e.message.orEmpty())
            emit(error(e.message.orEmpty()))
        }
    }

    override fun getTvShow(category: String) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.getTvByCategory(category).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(success(data))
                }
                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            Log.e("logError", e.message.orEmpty())
            emit(error(e.message.orEmpty()))
        }
    }
}