package id.outivox.core.data.remote.paging.movie

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.outivox.core.data.remote.source.network.ApiService
import id.outivox.core.data.remote.source.response.movie.MovieItem
import id.outivox.core.utils.localizedApiCodeErrorMessage
import id.outivox.core.utils.orZero
import id.outivox.core.utils.toJson
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GetRecommendationsMovies(
    private val apiService: ApiService,
    private val id: Int,
) : PagingSource<Int, MovieItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val pageNumber = params.key ?: 1

        return try {
            val response = apiService.getRecommendationsMovies(id = id, page = pageNumber)
            if (response != null) {
                if (response.results.isNullOrEmpty()) {
                    Log.e("logError", "Data: ${response.toJson()}")
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = if (pageNumber == 1) null else pageNumber - 1,
                        nextKey = if (response.totalPages.orZero() == pageNumber) null else (if (response.totalPages.orZero() > 0) pageNumber + 1 else null)
                    )
                } else {
                    Log.i("logInfo", "Data: ${response.toJson()}")
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (pageNumber == 1) null else pageNumber - 1,
                        nextKey = if (response.totalPages.orZero() == pageNumber) null else (if (response.totalPages.orZero() > 0) pageNumber + 1 else null)
                    )
                }
            } else {
                Log.e("logError", "Data: ${response?.toJson()}")
                LoadResult.Error(Exception("Data is empty"))
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

    override fun getRefreshKey(state: PagingState<Int, MovieItem>) = state.anchorPosition?.let { anchorPosition ->
        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
}