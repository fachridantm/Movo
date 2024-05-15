package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemCarouselHomeBinding

class CarouselAdapter(
    private val onItemClick: (Movie) -> Unit,
) : PagingDataAdapter<Movie, CarouselAdapter.CarouselViewHolder>(DIFF_CALLBACK) {
    inner class CarouselViewHolder(val binding: ItemCarouselHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                ivMoviePoster.loadImageWithOptions(data.posterPath)
                tvCarouselTitle.text = data.title
                root.setOnClickListener { onItemClick(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselViewHolder(
        ItemCarouselHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}