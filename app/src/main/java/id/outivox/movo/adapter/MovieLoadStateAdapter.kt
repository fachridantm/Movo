package id.outivox.movo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.outivox.movo.databinding.ItemMovieLoadStateBinding

class MovieLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieLoadStateAdapter.LoadingStateViewHolder>() {
    inner class LoadingStateViewHolder(
        private val binding: ItemMovieLoadStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvError.text = loadState.error.message
            }
            binding.apply {
                pbLoading.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error
                tvError.isVisible = loadState is LoadState.Error
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemMovieLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }
}