package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemReviewDetailBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    private var listReview = ArrayList<Review>()

    fun setData(list: List<Review>?) {
        if (list == null) return
        listReview.clear()
        listReview.addAll(list)
    }

    class MyViewHolder(val binding: ItemReviewDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemReviewDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            tvNameReview.text = listReview[position].name
            tvUsernameReview.text = "@"+listReview[position].username
            tvMessage.text = listReview[position].content
            imgProfileReview.loadImageWithOptions(listReview[position].avatarPath.drop(1))
        }
    }

    override fun getItemCount() = listReview.size
}