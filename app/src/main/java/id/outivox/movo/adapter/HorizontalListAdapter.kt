package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemHorizontalMovieBinding

class HorizontalListAdapter(
    private val onItemClick: (Any) -> Unit
) : PagingDataAdapter<Any, HorizontalListAdapter.HorizontalViewHolder>(DIFF_CALLBACK) {
    inner class HorizontalViewHolder(val binding: ItemHorizontalMovieBinding) : RecyclerView.ViewHolder(binding.root) {
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
                ivPoster.loadImageWithOptions(data.posterPath)
                root.setOnClickListener { onItemClick(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HorizontalViewHolder(
        ItemHorizontalMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
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