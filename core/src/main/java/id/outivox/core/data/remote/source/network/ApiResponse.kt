package id.outivox.core.data.remote.source.network

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    data object Empty : ApiResponse<Nothing>()

    companion object {
        fun <T> apiSuccess(data: T) = Success(data)
        fun apiError(message: String) = Error(message)
        fun apiEmpty() = Empty
    }
}