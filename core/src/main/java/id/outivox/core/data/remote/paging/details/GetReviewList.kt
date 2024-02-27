package id.outivox.core.data.remote.paging.details

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.outivox.core.data.remote.source.network.ApiService
import id.outivox.core.data.remote.source.response.detail.review.ReviewItem
import id.outivox.core.utils.localizedApiCodeErrorMessage
import id.outivox.core.utils.orZero
import id.outivox.core.utils.toJson
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class GetReviewList(
    private val apiService: ApiService,
    private val media: String,
    private val id: Int
) : PagingSource<Int, ReviewItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewItem> {
        val pageNumber = params.key ?: 1
        val pageSize = 20

        return try {
            val response = apiService.getReviewList(media = media, id = id, page = pageNumber)
            if (response != null) {
                if (response.results.isNullOrEmpty()) {
                    Log.e("logError", "Data: ${response.toJson()}")
                    LoadResult.Error(Exception("No more data"))
                } else {
                    Log.i("logInfo", "Data: ${response.toJson()}")
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (pageNumber == 1) null else pageNumber - 1,
                        nextKey = if (response.results.size.orZero() < pageSize) null else pageNumber + 1
                    )
                }
            } else {
                Log.e("logError", "Data: ${response?.toJson()}")
                LoadResult.Error(Exception("No more data"))
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

    override fun getRefreshKey(state: PagingState<Int, ReviewItem>) = state.anchorPosition?.let { anchorPosition ->
        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
}