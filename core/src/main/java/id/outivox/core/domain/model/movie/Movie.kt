package id.outivox.core.domain.model.movie

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val genres: List<String>,
    val releaseDate: String,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val adult: Boolean
)
