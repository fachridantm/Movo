package id.outivox.core.data.remote.source.response.detail.wallpaper

import com.google.gson.annotations.SerializedName

data class PostersItem(
	@field:SerializedName("file_path")
	val filePath: String? = null
)