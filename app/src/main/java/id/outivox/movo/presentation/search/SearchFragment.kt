package id.outivox.movo.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.movo.databinding.FragmentSearchBinding
import id.outivox.movo.presentation.search.fragment.adapter.SearchViewPagerAdapter


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding as FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)

        setUpMediaViewPager("")
        setUpSearchView()

        return binding.root
    }

    private fun setUpSearchView() {
        binding.svSearch.apply {
            requestFocus()
            binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query == null) return false
                    setUpMediaViewPager(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }

    }

    private fun setUpMediaViewPager(query: String) {
        binding.apply {
            vpSearch.isUserInputEnabled = false
            vpSearch.adapter = activity?.let { SearchViewPagerAdapter(it, query) }
            TabLayoutMediator(searchMediaTabs, vpSearch){ tab, position ->
                when (position) {
                    0 -> tab.text = "Movie"
                    1 -> tab.text = "Tv Show"
                }
            }.attach()
        }
    }
}