package id.outivox.movo.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemVerticalMovieBinding
import id.outivox.movo.utils.setupRatingStars

class FavoriteAdapter(
    private val onItemClick: (Any) -> Unit,
) : ListAdapter<Any, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {
    inner class FavoriteViewHolder(val binding: ItemVerticalMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            when (data) {
                is MovieDetail -> bindMovieItem(data)
                is TvDetail -> bindTvItem(data)
            }
        }

        private fun bindTvItem(data: TvDetail) {
            binding.apply {
                tvTitle.text = data.title
                tvGenre.text = data.genres.joinToString(", ")
                setupRatingStars(data.voteAverage)
                ivPoster.loadImageWithOptions(data.posterPath)
                root.setOnClickListener { onItemClick(data) }
            }
        }

        private fun bindMovieItem(data: MovieDetail) {
            binding.apply {
                tvTitle.text = data.title
                tvGenre.text = data.genres.joinToString(", ")
                setupRatingStars(data.voteAverage)
                ivPoster.loadImageWithOptions(data.posterPath)
                root.setOnClickListener { onItemClick(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteViewHolder(
        ItemVerticalMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
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