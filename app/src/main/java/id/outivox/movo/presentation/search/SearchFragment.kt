package id.outivox.movo.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.movo.databinding.FragmentSearchBinding
import id.outivox.movo.presentation.search.fragment.adapter.SearchMediaAdapter


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding as FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAction()
        setupMediaViewPager()
        setupSearchView()
    }

    private fun initAction() {
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setupMediaViewPager(query: String = "") {
        binding.apply {
            vpSearch.apply {
                isUserInputEnabled = true
                adapter = SearchMediaAdapter(requireActivity(), query)
            }

            TabLayoutMediator(searchMediaTabs, vpSearch){ tab, position ->
                when (position) {
                    0 -> tab.text = "Movie"
                    1 -> tab.text = "Tv Show"
                }
            }.attach()
        }
    }

    private fun setupSearchView() {
        binding.svSearch.apply {
            requestFocus()
            binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query.isNullOrEmpty()) return false
                    setupMediaViewPager(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }

    }
}