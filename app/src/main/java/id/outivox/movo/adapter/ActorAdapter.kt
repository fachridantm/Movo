package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemCreditDetailBinding

class ActorAdapter(private val onItemClick: (Actor) -> Unit) : ListAdapter<Actor, ActorAdapter.ActorViewHolder>(DIFF_CALLBACK) {
    inner class ActorViewHolder(val binding: ItemCreditDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Actor) {
            binding.apply {
                tvCharacter.text = data.character
                tvName.text = data.name
                ivProfileCredit.loadImageWithOptions(data.profilePath)
                root.setOnClickListener { onItemClick(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActorViewHolder(
        ItemCreditDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Actor>() {
            override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem.character == newItem.character
            }

            override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
                return oldItem == newItem
            }
        }
    }
}