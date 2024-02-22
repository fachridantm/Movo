package id.outivox.core.domain.model.detail

data class Review(
	val avatarPath: String,
	val name: String,
	val rating: Double,
	val username: String,
	val content: String
)