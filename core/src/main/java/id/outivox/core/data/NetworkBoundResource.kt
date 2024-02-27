package id.outivox.core.data

import id.outivox.core.data.remote.source.network.ApiResponse
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.Resource.Companion.empty
import id.outivox.core.domain.model.Resource.Companion.loading
import id.outivox.core.domain.model.Resource.Companion.success
import id.outivox.core.domain.model.Resource.Companion.error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(loading())
            when (val response = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(response.data)
                    emitAll(loadFromDB().map { success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { empty() })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(error(response.message))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}