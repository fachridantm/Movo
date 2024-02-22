package id.outivox.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import id.outivox.movo.core.BuildConfig.IMAGE_BASE_URL
import id.outivox.movo.core.R

fun ImageView.loadImageWithOptions(path: String, widthOverride: Int? = null, heightOverride: Int? = null) {
    Glide.with(this.context)
        .load(IMAGE_BASE_URL + path)
        .apply(RequestOptions())
        .override(widthOverride ?: this.width, heightOverride ?: this.height)
        .placeholder(android.R.color.darker_gray)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .into(this)
}

fun ImageView.loadImageOnly(path: String) {
    Glide.with(this.context)
        .load(IMAGE_BASE_URL + path)
        .placeholder(android.R.color.darker_gray)
        .into(this)
}