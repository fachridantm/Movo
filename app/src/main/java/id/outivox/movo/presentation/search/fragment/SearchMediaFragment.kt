package id.outivox.movo.presentation.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.paging.map
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_SEARCH_QUERY
import id.outivox.core.utils.Constants.MOVIE
import id.outivox.core.utils.Constants.TV_SHOW
import id.outivox.core.utils.showSnackbar
import id.outivox.movo.R
import id.outivox.movo.adapter.MovieLoadStateAdapter
import id.outivox.movo.adapter.VerticalListAdapter
import id.outivox.movo.databinding.FragmentTabMediaBinding
import id.outivox.movo.presentation.detail.DetailActivity
import id.outivox.movo.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMediaFragment : Fragment() {

    private var _binding: FragmentTabMediaBinding? = null
    private val binding get() = _binding as FragmentTabMediaBinding

    private val viewModel: SearchViewModel by viewModel()
    private val verticalAdapter by lazy { VerticalListAdapter(::onItemClick) }
    private val mediaType by lazy { arguments?.getString(BUNDLE_MEDIA_TYPE).orEmpty() }
    private val query by lazy { arguments?.getString(BUNDLE_SEARCH_QUERY).orEmpty() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabMediaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initData()
        initView()
    }

    private fun initObservers() {
        if (mediaType == BUNDLE_MEDIA_MOVIE) viewModel.movies.observe(viewLifecycleOwner, ::setupMoviesData)
        else viewModel.tvShow.observe(viewLifecycleOwner, ::setupTvShowData)
    }

    private fun initData() {
        if (mediaType.equals(BUNDLE_MEDIA_MOVIE, true)) viewModel.searchMovie(query) else viewModel.searchTv(query)
    }

    private fun initView() {
        with(binding) {
            rvResults.adapter = verticalAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { verticalAdapter.retry() }
            )
        }
    }

    private fun setupMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                verticalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupTvShowData(resource: Resource<PagingData<Tv>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                verticalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun onItemClick(any: Any) {
        when (any) {
            is Movie -> DetailActivity.start(requireContext(), any.id, MOVIE)
            is Tv -> DetailActivity.start(requireContext(), any.id, TV_SHOW)
        }
    }
}