package id.outivox.movo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.utils.Constants.EXTRA_MOVIE_ID
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemCarouselHomeBinding
import id.outivox.movo.`interface`.GetCurrentPosition
import id.outivox.movo.presentation.detail.DetailActivity

class CarouselAdapter : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private val listMovie = ArrayList<Movie>()

    fun setData(data: List<Movie>?) {
        if (data == null) return
        listMovie.clear()
        listMovie.addAll(data)
    }

    private var getCurrentPosition: GetCurrentPosition? = null

    fun getCurrentItemPosition(getCurrentPosition: GetCurrentPosition) {
        this.getCurrentPosition = getCurrentPosition
    }

    class CarouselViewHolder(val binding: ItemCarouselHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarouselViewHolder(
        ItemCarouselHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.binding.apply {
            imgMovie.loadImageWithOptions(listMovie[position].posterPath)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, listMovie[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listMovie.size

}