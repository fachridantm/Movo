package id.outivox.core.domain.model.tv

data class TvResult(
	val page: Int = 1,
	val totalPages: Int,
	val totalResults: Int,
	val tv: List<Tv>
)