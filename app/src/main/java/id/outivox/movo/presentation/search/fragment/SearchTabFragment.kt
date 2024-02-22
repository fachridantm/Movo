package id.outivox.movo.presentation.search.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_SEARCH_QUERY
import id.outivox.core.utils.Constants.EXTRA_MEDIA_MOVIE
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TV
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TYPE
import id.outivox.core.utils.Constants.EXTRA_MOVIE_ID
import id.outivox.movo.adapter.VerticalListAdapter
import id.outivox.movo.databinding.FragmentSearchTabBinding
import id.outivox.movo.`interface`.OnItemClickCallback
import id.outivox.movo.presentation.detail.DetailActivity
import id.outivox.movo.presentation.search.SearchViewModel
import id.outivox.movo.presentation.search.fragment.adapter.SearchMediaViewPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchTabFragment : Fragment() {

    private var _binding: FragmentSearchTabBinding? = null
    private val binding get() = _binding as FragmentSearchTabBinding

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var query: String
    private var page = 1
    private lateinit var mediaType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchTabBinding.inflate(layoutInflater)

        query = arguments?.getString(BUNDLE_SEARCH_QUERY) ?: ""
        page = arguments?.getInt(SearchMediaViewPagerAdapter.BUNDLE_SEARCH_PAGE) ?: 1
        mediaType = arguments?.getString(BUNDLE_MEDIA_TYPE) ?: ""

        initObserver()

        return binding.root
    }

    private fun initObserver() {
        when(mediaType){
            BUNDLE_MEDIA_MOVIE -> {
                viewModel.searchMovie(query, page.toString())
                viewModel.movieResponse.observe(viewLifecycleOwner){
                    setUpSearchRV(it)
                }
            }
            BUNDLE_MEDIA_TV -> {
                viewModel.searchTv(query)
                viewModel.tvResponse.observe(viewLifecycleOwner){
                    setUpSearchRV(it)
                }
            }
        }
    }

    private fun <T> setUpSearchRV(resource: Resource<T>?) {
        when(resource){
            is Resource.Success -> {
                when(resource.data){
                    is MovieResult -> {
                        val result = resource.data as MovieResult
                        displayMovie(result)
                    }
                    is TvResult -> {
                        val result = resource.data as TvResult
                        displayTv(result)
                    }
                }
            }
            else -> {}
        }
    }

    private fun displayTv(result: TvResult) {
        binding.rvSearch.apply {
            val mAdapter = VerticalListAdapter<Tv>()
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            mAdapter.setData(result.tv)
            mAdapter.setOnItemClickCallback(
                object : OnItemClickCallback{
                    override fun onItemClicked(id: Int) {
                        startActivity(
                            Intent(context, DetailActivity::class.java)
                                .putExtra(EXTRA_MOVIE_ID, id)
                                .putExtra(EXTRA_MEDIA_TYPE, EXTRA_MEDIA_TV)
                        )
                    }
                }
            )
        }
    }

    private fun displayMovie(result: MovieResult) {
        binding.rvSearch.apply {
            val mAdapter = VerticalListAdapter<Movie>()
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            mAdapter.setData(result.movie)
            mAdapter.setOnItemClickCallback(
                object : OnItemClickCallback{
                    override fun onItemClicked(id: Int) {
                        startActivity(
                            Intent(context, DetailActivity::class.java)
                                .putExtra(EXTRA_MOVIE_ID, id)
                                .putExtra(EXTRA_MEDIA_TYPE, EXTRA_MEDIA_MOVIE)
                        )
                    }
                }
            )
        }
    }
}