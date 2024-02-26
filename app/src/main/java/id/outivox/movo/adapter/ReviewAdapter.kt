package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.R
import id.outivox.movo.databinding.ItemReviewDetailBinding
import id.outivox.movo.utils.setupRatingStars

class ReviewAdapter : PagingDataAdapter<Review, ReviewAdapter.ReviewViewHolder>(DIFF_CALLBACK) {
    inner class ReviewViewHolder(val binding: ItemReviewDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Review) {
            binding.apply {
                tvNameReview.text = data.name
                tvUsernameReview.text = itemView.context.getString(R.string.username_review, data.username)
                tvReviewMessage.text = data.content
                setupRatingStars(data.rating)
                ivProfileReview.loadImageWithOptions(data.avatarPath)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolder(
        ItemReviewDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }
}