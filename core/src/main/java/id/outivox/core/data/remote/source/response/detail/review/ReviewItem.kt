package id.outivox.core.data.remote.source.response.detail.review

import com.google.gson.annotations.SerializedName

data class ReviewItem(
    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("author_details")
    val authorDetails: AuthorDetails? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)