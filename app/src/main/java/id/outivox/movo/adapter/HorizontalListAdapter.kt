package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemHorizontalMovieBinding
import id.outivox.movo.`interface`.OnItemClickCallback

class HorizontalListAdapter<T> : RecyclerView.Adapter<HorizontalListAdapter.MyViewHolder>() {

    val listMovie = ArrayList<T>()

    private var onItemClickCallBack: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    fun setData(data: List<T>?) {
        if (data == null) return
        listMovie.clear()
        listMovie.addAll(data)
    }

    class MyViewHolder(val binding: ItemHorizontalMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemHorizontalMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            when (listMovie[position]) {
                is Movie -> {
                    with(listMovie[position] as Movie) {
                        imgMovie.loadImageWithOptions(posterPath)
                        tvTitle.text = title

                        holder.itemView.setOnClickListener {
                            onItemClickCallBack?.onItemClicked(id)
                        }
                    }
                }

                is Tv -> {
                    with(listMovie[position] as Tv) {
                        imgMovie.loadImageWithOptions(posterPath, 300, 300)
                        tvTitle.text = name

                        holder.itemView.setOnClickListener {
                            onItemClickCallBack?.onItemClicked(id)
                        }
                    }
                }
            }
        }

    }

    override fun getItemCount() = listMovie.size
}