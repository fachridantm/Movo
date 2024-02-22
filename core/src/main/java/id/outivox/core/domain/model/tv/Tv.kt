package id.outivox.core.domain.model.tv

data class Tv (
    val id: Int,
    val title: String,
    val genre: List<String>,
    val posterPath: String,
    val voteAverage: Double,
    val firstAirDate: String
)
