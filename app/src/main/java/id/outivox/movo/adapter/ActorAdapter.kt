package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.utils.loadImageWithOptions
import id.outivox.movo.databinding.ItemCreditDetailBinding

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.MyViewHolder>() {
    private val listCredit = ArrayList<Actor>()

    class MyViewHolder(val binding: ItemCreditDetailBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(list: List<Actor>?) {
        if (list == null) return
        listCredit.clear()
        listCredit.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemCreditDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            tvName.text = listCredit[position].name
            tvCharacter.text = listCredit[position].character
            imgProfileCredit.loadImageWithOptions(listCredit[position].profilePath)
        }
    }

    override fun getItemCount() = listCredit.size
}