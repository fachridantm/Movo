package id.outivox.core.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import id.outivox.core.utils.Constants.AIRING_TODAY_TV
import id.outivox.core.utils.Constants.API_DATE_FORMAT
import id.outivox.core.utils.Constants.JAKARTA_TIME_ZONE
import id.outivox.core.utils.Constants.LATEST_MOVIE
import id.outivox.core.utils.Constants.NOW_PLAYING_MOVIE
import id.outivox.core.utils.Constants.ON_THE_AIR_TV
import id.outivox.core.utils.Constants.POPULAR_MOVIE
import id.outivox.core.utils.Constants.POPULAR_TV
import id.outivox.core.utils.Constants.TOP_RATED_MOVIE
import id.outivox.core.utils.Constants.TOP_RATED_TV
import id.outivox.core.utils.Constants.UI_DATE_FORMAT
import id.outivox.core.utils.Constants.UPCOMING_MOVIE
import id.outivox.movo.core.BuildConfig.IMAGE_BASE_URL
import org.json.JSONObject
import retrofit2.HttpException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

var localeId = Locale.getDefault().language.equals("in", ignoreCase = true)

fun ImageView.loadImageWithOptions(path: String, widthOverride: Int? = null, heightOverride: Int? = null) {
    Glide.with(this.context)
        .load(IMAGE_BASE_URL + path)
        .apply(RequestOptions())
        .override(widthOverride ?: this.width, heightOverride ?: this.height)
        .error(android.R.color.darker_gray)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .into(this)
}

fun ImageView.loadImageOnly(path: String) {
    Glide.with(this.context)
        .load(IMAGE_BASE_URL + path)
        .error(android.R.color.darker_gray)
        .into(this)
}

fun String.showSnackbar(
    view: View,
    anchorView: View? = null,
    duration: Int = Snackbar.LENGTH_SHORT,
) {
    Snackbar.make(view, this, duration).apply {
        if (anchorView != null) this.anchorView = anchorView
        show()
    }
}

fun Int?.orZero() = this ?: 0

fun Double?.orZero() = this ?: 0.0

fun Long?.orZero() = this ?: 0L

fun String?.orZero() = this ?: "0"

fun Boolean?.orFalse() = this ?: false

fun Int.toMovieDurationFormat() = "${this / 60}h ${this % 60}min"

fun checkCurrentLocale(isLocale: Boolean): Locale = if (isLocale) Locale("in") else Locale.ENGLISH

fun String?.reformatDate(
    inputFormat: String = API_DATE_FORMAT,
    outputFormat: String = UI_DATE_FORMAT,
    isLocale: Boolean = localeId,
): String {
    if (this.isNullOrEmpty()) return ""
    val calendar = Calendar.getInstance()
    val input = SimpleDateFormat(inputFormat, checkCurrentLocale(isLocale))
    val output = SimpleDateFormat(outputFormat, checkCurrentLocale(isLocale))
    try {
        calendar.apply {
            timeInMillis = input.parse(this@reformatDate)?.time.orZero()
            timeZone = TimeZone.getTimeZone(JAKARTA_TIME_ZONE)
        }
    } catch (e: ParseException) {
        Log.e("reformat", "reformat: ${e.message}")
    }
    return output.format(calendar.time)
}

fun Activity.setTransparentStatusBar() {
    val w: Window = this.window
    w.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}

fun HttpException.localizedApiCodeErrorMessage(): String {
    return when (this.code()) {
        400 -> "Bad Request: The request was invalid or cannot be otherwise served."
        401 -> "Unauthorized: Access is denied due to invalid credentials."
        403 -> "Forbidden: You don't have permission to access."
        404 -> "Not Found: The specified resource could not be found."
        405 -> "Invalid format: This service doesn't exist in that format."
        406 -> "Not Acceptable: Invalid accept header."
        408 -> "Request Timeout: Your request timed out. Please retry the request."
        409 -> "Conflict: The request could not be completed due to a conflict with the current state of the target resource."
        410 -> "Gone: The requested resource is no longer available."
        411 -> "Length Required: Content-Length header required."
        412 -> "Precondition Failed: The precondition given in the request evaluated to false by the server."
        413 -> "Payload Too Large: The data value transmitted exceeds the capacity limit."
        414 -> "URI Too Long: The data value transmitted exceeds the capacity limit."
        415 -> "Unsupported Media Type: The request entity has a media type which the server or resource does not support."
        416 -> "Range Not Satisfiable: The client has asked for a portion of the file, but the server cannot supply that portion."
        417 -> "Expectation Failed: The server cannot meet the requirements of the Expect request-header field."
        418 -> "I'm a teapot: The server refuses the attempt to brew coffee with a teapot."
        422 -> "Invalid parameters: Your request parameters are incorrect."
        429 -> "Too Many Requests: Your request count (#) is over the allowed limit of (40)."
        500 -> "Failed: Internal server error."
        501 -> "Invalid service: this service does not exist."
        502 -> "Bad Gateway: Couldn't connect to the backend server."
        503 -> "Service Unavailable: The service is temporarily unavailable. Try again later."
        504 -> "Gateway Timeout: Your request to the backend server timed out. Try again."
        505 -> "HTTP Version Not Supported: The HTTP version used in the request is not supported."
        else -> this.errorMessage().orEmpty()
    }
}

fun HttpException.errorMessage(): String? {
    val response = this.response()?.errorBody()?.string()
    if (response == null || response.isEmpty()) return "Unknown Error: Something went wrong. Please try again later."
    return try {
        val jsonObject = JSONObject(response)
        jsonObject.getString("message")
    } catch (e: Exception) {
        e.printStackTrace()
        e.message
    }
}

fun Any?.toJson(): String {
    if (this == null) return "null"
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    return gson.toJson(this)
}

fun String?.jsonToString(): String {
    return try {
        val jsonObject = JSONObject(this.orEmpty())
        jsonObject.toString(4)
    } catch (e: Exception) {
        e.printStackTrace()
        e.message.orEmpty()
    }
}

fun Int?.toGenreFormat(): String {
    return when (this) {
        28 -> "Action"
        12 -> "Adventure"
        16 -> "Animation"
        35 -> "Comedy"
        80 -> "Crime"
        99 -> "Documentary"
        18 -> "Drama"
        10751 -> "Family"
        14 -> "Fantasy"
        36 -> "History"
        27 -> "Horror"
        10402 -> "Music"
        9648 -> "Mystery"
        10749 -> "Romance"
        878 -> "Science Fiction"
        10770 -> "TV Movie"
        53 -> "Thriller"
        10752 -> "War"
        37 -> "Western"
        else -> "Unknown"
    }
}

@Suppress("DUPLICATE_LABEL_IN_WHEN")
fun String?.toCategoryTitle(): String {
    return when (this) {
        POPULAR_MOVIE -> "Popular Movie"
        TOP_RATED_MOVIE -> "Top Rated Movie"
        UPCOMING_MOVIE -> "Upcoming Movie"
        NOW_PLAYING_MOVIE -> "Now Playing Movie"
        LATEST_MOVIE -> "Latest Movie"
        AIRING_TODAY_TV -> "Airing Today TV"
        POPULAR_TV -> "Popular TV"
        TOP_RATED_TV -> "Top Rated TV"
        ON_THE_AIR_TV -> "On The Air TV"
        else -> this.orEmpty()
    }
}