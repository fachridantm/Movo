package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemVerticalMovieBinding
import id.outivox.movo.`interface`.OnItemClickCallback
import id.outivox.movo.utils.HelperFunction

class VerticalListAdapter<T> : RecyclerView.Adapter<VerticalListAdapter.VerticalViewholder>() {

    val listMovie = ArrayList<T>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(list: List<T>?) {
        if (list == null) return
        listMovie.clear()
        listMovie.addAll(list)
    }

    class VerticalViewholder(val binding: ItemVerticalMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VerticalViewholder(
        ItemVerticalMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VerticalViewholder, position: Int) {
        holder.binding.apply {
            when (listMovie[position]) {
                is Movie -> {
                    with(listMovie[position] as Movie) {
                        tvTitle.text = title
                        tvGenre.text = HelperFunction.genreComaFormatter(genre)
                        HelperFunction.setUpRatingStars(this@apply, voteAverage)
                        imgPoster.loadImageWithOptions(posterPath, 300, 300)

                        holder.itemView.setOnClickListener {
                            onItemClickCallback?.onItemClicked(id)
                        }
                    }
                }

                is Tv -> {
                    with(listMovie[position] as Tv) {
                        tvTitle.text = name
                        tvGenre.text = HelperFunction.genreComaFormatter(genre)
                        HelperFunction.setUpRatingStars(this@apply, voteAverage)
                        imgPoster.loadImageWithOptions(posterPath)

                        holder.itemView.setOnClickListener {
                            onItemClickCallback?.onItemClicked(id)
                        }
                    }
                }
            }

        }


    }

    override fun getItemCount() = listMovie.size
}