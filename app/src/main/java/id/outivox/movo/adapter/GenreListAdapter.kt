package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.outivox.movo.databinding.ItemGenreListBinding

class GenreListAdapter() : ListAdapter<List<String>, GenreListAdapter.GenreViewHolder>(DIFF_CALLBACK) {
    inner class GenreViewHolder(val binding: ItemGenreListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: List<String>) {
            binding.apply {
                tvGenre.text = data.joinToString(", ")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenreViewHolder(
        ItemGenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    override fun getItemCount() = if (currentList.size <= 3) currentList.size else 3

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<List<String>>() {
            override fun areItemsTheSame(oldItem: List<String>, newItem: List<String>): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: List<String>, newItem: List<String>): Boolean {
                return oldItem.size == newItem.size
            }
        }
    }
}