package id.outivox.movo.presentation.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.paging.map
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_ID
import id.outivox.core.utils.Constants.MOVIE
import id.outivox.core.utils.Constants.TV_SHOW
import id.outivox.core.utils.orZero
import id.outivox.core.utils.showSnackbar
import id.outivox.movo.R
import id.outivox.movo.adapter.HorizontalListAdapter
import id.outivox.movo.adapter.MovieLoadStateAdapter
import id.outivox.movo.databinding.FragmentOtherBinding
import id.outivox.movo.presentation.detail.DetailActivity
import id.outivox.movo.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtherFragment : Fragment() {

    private var _binding: FragmentOtherBinding? = null
    private val binding get() = _binding as FragmentOtherBinding

    private val viewModel: DetailViewModel by viewModel()
    private val horizontalAdapter: HorizontalListAdapter by lazy { HorizontalListAdapter(::onItemClick) }

    private var id: Int = 0
    private var isMovie: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getInt(BUNDLE_MOVIE_ID).orZero()
        isMovie = arguments?.getString(BUNDLE_MEDIA_TYPE).equals(BUNDLE_MEDIA_MOVIE)

        initView()
        initData()
        initObserver()
    }

    private fun initView() {
        with(binding) {
            rvSimilar.apply {
                adapter = horizontalAdapter.withLoadStateFooter(
                    footer = MovieLoadStateAdapter { horizontalAdapter.retry() }
                )
            }
        }
    }

    private fun initData() {
        viewModel.apply {
            if (isMovie) {
                getSimilarMovies(id)
                getMovieReviews(id)
                getRecommendationsMovies(id)
            } else {
                getSimilarTv(id)
                getTvReviews(id)
                getRecommendationsTv(id)
            }
        }
    }

    private fun initObserver() {
        viewModel.apply {
            if (isMovie) {
                movieSimilar.observe(viewLifecycleOwner, ::setupSimilarMoviesData)
                movieRecommendations.observe(viewLifecycleOwner, ::setupRecommendationsMoviesData)
                review.observe(viewLifecycleOwner, ::setupReviewsMovieData)
            } else {
                tvSimilar.observe(viewLifecycleOwner, ::setupSimilarTvData)
                tvRecommendations.observe(viewLifecycleOwner, ::setupRecommendationsTvData)
                review.observe(viewLifecycleOwner, ::setupReviewsTvData)
            }
        }
    }

    private fun setupReviewsTvData(resource: Resource<PagingData<Review>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                horizontalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupRecommendationsTvData(resource: Resource<PagingData<Tv>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                horizontalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupSimilarTvData(resource: Resource<PagingData<Tv>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                horizontalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupReviewsMovieData(resource: Resource<PagingData<Review>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                horizontalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupRecommendationsMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                horizontalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupSimilarMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data: PagingData<Any> = resource.data.map { it }
                horizontalAdapter.submitData(lifecycle, data)
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