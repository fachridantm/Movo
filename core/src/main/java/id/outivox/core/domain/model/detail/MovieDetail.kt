package id.outivox.core.domain.model.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
	val id: Int,
	val title: String,
	val genres: List<String>,
	val overview: String,
	val video: Boolean,
	val duration: Int,
	val posterPath: String,
	val releaseDate: String,
	val voteCount: Int,
	val voteAverage: Double,
): Parcelable