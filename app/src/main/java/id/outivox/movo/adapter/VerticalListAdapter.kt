package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemVerticalMovieBinding
import id.outivox.movo.utils.setupRatingStars

class VerticalListAdapter(
    private val onItemClick: (Any) -> Unit
) : PagingDataAdapter<Any, VerticalListAdapter.VerticalViewholder>(DIFF_CALLBACK) {
    inner class VerticalViewholder(val binding: ItemVerticalMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            when (data) {
                is Movie -> bindMovieItem(data)
                is Tv -> bindTvItem(data)
            }
        }

        private fun bindTvItem(data: Tv) {
            binding.apply {
                tvTitle.text = data.title
                tvGenre.text = data.genres.joinToString(", ")
                ivPoster.loadImageWithOptions(data.posterPath)
                root.setOnClickListener { onItemClick(data) }
            }
        }

        private fun bindMovieItem(data: Movie) {
            binding.apply {
                tvTitle.text = data.title
                tvGenre.text = data.genres.joinToString(", ")
                setupRatingStars(data.voteAverage)
                ivPoster.loadImageWithOptions(data.posterPath)
                root.setOnClickListener { onItemClick(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VerticalViewholder(
        ItemVerticalMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VerticalViewholder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return when {
                    oldItem is Movie && newItem is Movie -> oldItem == newItem
                    oldItem is Tv && newItem is Tv -> oldItem == newItem
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return when {
                    oldItem is Movie && newItem is Movie -> oldItem.id == newItem.id
                    oldItem is Tv && newItem is Tv -> oldItem.id == newItem.id
                    else -> false
                }
            }
        }
    }
}