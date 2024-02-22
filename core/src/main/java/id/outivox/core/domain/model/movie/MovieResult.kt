package id.outivox.core.domain.model.movie

data class MovieResult(
	val page: Int,
	val totalPages: Int,
	val totalResults: Int,
	val movie: List<Movie>
)