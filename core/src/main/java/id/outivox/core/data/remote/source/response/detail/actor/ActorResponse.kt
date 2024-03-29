package id.outivox.core.data.remote.source.response.detail.actor

import com.google.gson.annotations.SerializedName

data class ActorResponse(

	@field:SerializedName("cast")
	val cast: List<CastItem>? = null
)