package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.utils.loadImageOnly
import id.outivox.movo.databinding.ItemWallpaperBinding

class WallpaperAdapter : ListAdapter<Wallpaper, WallpaperAdapter.WallpaperViewHolder>(DIFF_CALLBACK) {
    inner class WallpaperViewHolder(val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Wallpaper, position: Int) {
            binding.ivWallpaper.loadImageOnly(data.wallpaperUrl[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WallpaperViewHolder(
        ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item, position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Wallpaper>() {
            override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
                return oldItem.wallpaperUrl == newItem.wallpaperUrl
            }

            override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper): Boolean {
                return oldItem == newItem
            }
        }
    }
}