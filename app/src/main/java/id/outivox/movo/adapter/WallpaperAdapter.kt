package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.utils.loadImageOnly
import id.outivox.movo.databinding.ItemWallpaperBinding

class WallpaperAdapter : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    val listWallpaper = arrayListOf<String>()

    fun setData(list: List<String>) {
        listWallpaper.clear()
        listWallpaper.addAll(list)
    }

    class WallpaperViewHolder(val binding: ItemWallpaperBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WallpaperViewHolder(
        ItemWallpaperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.apply {
            binding.imgWallpaper.loadImageOnly(listWallpaper[position])
        }
    }

    override fun getItemCount() = listWallpaper.size
}