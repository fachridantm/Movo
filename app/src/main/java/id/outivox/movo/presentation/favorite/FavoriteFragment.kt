package id.outivox.movo.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.movo.databinding.FragmentFavoriteBinding
import id.outivox.movo.presentation.favorite.adapter.FavoriteMediaAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMediaViewPager()
    }

    private fun setupMediaViewPager() {
        binding.apply {
            vpFavorite.apply {
                isUserInputEnabled = true
                adapter = FavoriteMediaAdapter(requireActivity())
            }

            TabLayoutMediator(favoriteMediaTabs, vpFavorite) { tab, position ->
                when (position) {
                    0 -> tab.text = "Movie"
                    1 -> tab.text = "Tv Show"
                }
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}