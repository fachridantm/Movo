package id.outivox.core.data.repository.home

import android.util.Log
import androidx.paging.map
import id.outivox.core.data.local.LocalDataSource
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.network.ApiResponse
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.repository.home.HomeRepository
import id.outivox.core.mapper.MovieMapper.map
import id.outivox.core.mapper.TvMapper.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(val remoteDataSource: RemoteDataSource, val localDataSource: LocalDataSource) : HomeRepository {
    override fun getMovies(category: String, region: String) = flow {
        emit(Resource.loading())
        try {
            when (val response = remoteDataSource.getMoviesByCategory(category, region).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(Resource.success(data))
                }

                is ApiResponse.Empty -> emit(Resource.empty())
                is ApiResponse.Error -> emit(Resource.error(response.message))
            }
        } catch (e: Exception) {
            Log.e("logError", e.message.orEmpty())
            emit(Resource.error(e.message.orEmpty()))
        }
    }

    override fun getTvShow(category: String) = flow {
        emit(Resource.loading())
        try {
            when (val response = remoteDataSource.getTvByCategory(category).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(Resource.success(data))
                }

                is ApiResponse.Empty -> emit(Resource.empty())
                is ApiResponse.Error -> emit(Resource.error(response.message))
            }
        } catch (e: Exception) {
            Log.e("logError", e.message.orEmpty())
            emit(Resource.error(e.message.orEmpty()))
        }
    }

    override fun saveRegion(region: String) {
        localDataSource.saveRegion(region)
    }
}