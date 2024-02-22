package id.outivox.core.data.repository.search

import androidx.paging.map
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.network.ApiResponse
import id.outivox.core.domain.model.Resource.Companion.empty
import id.outivox.core.domain.model.Resource.Companion.error
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.Resource.Companion.success
import id.outivox.core.domain.repository.search.SearchRepository
import id.outivox.core.mapper.MovieMapper.map
import id.outivox.core.mapper.TvMapper.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(val remoteDataSource: RemoteDataSource) : SearchRepository {
    override fun searchMovie(query: String, region: String) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.searchMovieByQuery(query, region).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            emit(error(e.message.orEmpty()))
        }
    }

    override fun searchTvShow(query: String) = flow {
        emit(loading())
        try {
            when (val response = remoteDataSource.searchTvByQuery(query).first()) {
                is ApiResponse.Success -> {
                    val data = response.data.map { it.map() }
                    emit(success(data))
                }

                is ApiResponse.Empty -> emit(empty())
                is ApiResponse.Error -> emit(error(response.message))
            }
        } catch (e: Exception) {
            emit(error(e.message.orEmpty()))
        }
    }
}