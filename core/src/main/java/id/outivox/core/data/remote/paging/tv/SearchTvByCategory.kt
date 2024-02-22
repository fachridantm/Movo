package id.outivox.core.data.remote.paging.tv

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.outivox.core.data.remote.source.network.ApiService
import id.outivox.core.data.remote.source.response.tv.TvItem
import id.outivox.core.utils.localizedApiCodeErrorMessage
import id.outivox.core.utils.orZero
import id.outivox.core.utils.toJson
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SearchTvByCategory(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, TvItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvItem> {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize

        return try {
            val response = apiService.searchTvByQuery(query = query, page = pageNumber)

            if (response.results.isNullOrEmpty()) {
                Log.e("logError", "Data: ${response.toJson()}")
                LoadResult.Error(Exception("No more data"))
            } else {
                Log.i("logInfo", "Data: ${response.toJson()}")
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (pageNumber == 1) null else (pageNumber * pageSize) - pageSize,
                    nextKey = if (response.totalPages.orZero() < pageSize) null else pageNumber + 1
                )
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    Log.e("logError", e.localizedApiCodeErrorMessage())
                    LoadResult.Error(Exception(e.localizedApiCodeErrorMessage()))
                }

                is UnknownHostException -> LoadResult.Error(Exception("No internet connection"))

                is SocketTimeoutException -> LoadResult.Error(Exception("Connection timeout"))

                else -> {
                    Log.e("logError", e.message.orEmpty())
                    LoadResult.Error(Exception("Something went wrong"))
                }
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvItem>) = state.anchorPosition
}