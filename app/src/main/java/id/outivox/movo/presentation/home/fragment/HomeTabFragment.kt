package id.outivox.movo.presentation.home.fragment

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
import id.outivox.core.utils.Constants
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_CATEGORY
import id.outivox.core.utils.Constants.NOW_PLAYING_MOVIE
import id.outivox.core.utils.showSnackbar
import id.outivox.movo.R
import id.outivox.movo.adapter.MovieLoadStateAdapter
import id.outivox.movo.adapter.VerticalListAdapter
import id.outivox.movo.databinding.FragmentHomeTabBinding
import id.outivox.movo.presentation.allmovietv.AllMovieTvViewModel
import id.outivox.movo.presentation.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeTabFragment : Fragment() {

    private val viewModel: AllMovieTvViewModel by viewModel()

    private var _binding: FragmentHomeTabBinding? = null
    private val binding get() = _binding as FragmentHomeTabBinding

    private val verticalAdapter by lazy { VerticalListAdapter(::onItemClick) }

    private var isMovie: Boolean = false
    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeTabBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isMovie = arguments?.getString(BUNDLE_MEDIA_TYPE).equals(BUNDLE_MEDIA_MOVIE)
        category = arguments?.getString(BUNDLE_MOVIE_CATEGORY) ?: NOW_PLAYING_MOVIE

        initData()
        initView()
        initObservers()
    }

    private fun initData() {
        viewModel.apply {
            if (isMovie) getMoviesByCategory(category) else getTvShowByCategory(category)
        }
    }

    private fun initView() {
        with(binding) {
            rvHome.adapter = verticalAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { verticalAdapter.retry() }
            )
        }
    }

    private fun initObservers() {
        if (isMovie) viewModel.movies.observe(viewLifecycleOwner, ::setupMoviesData)
        else viewModel.tvShow.observe(viewLifecycleOwner, ::setupTvShowData)
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
            is Movie -> DetailActivity.start(requireContext(), any.id, Constants.MOVIE)
            is Tv -> DetailActivity.start(requireContext(), any.id, Constants.TV_SHOW)
        }
    }
}