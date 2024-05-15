package id.outivox.movo.presentation.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Review
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_ID
import id.outivox.core.utils.Constants.MOVIE
import id.outivox.core.utils.Constants.TV_SHOW
import id.outivox.core.utils.orZero
import id.outivox.core.utils.showSnackbar
import id.outivox.movo.R
import id.outivox.movo.adapter.HorizontalListAdapter
import id.outivox.movo.adapter.MovieLoadStateAdapter
import id.outivox.movo.adapter.ReviewAdapter
import id.outivox.movo.databinding.FragmentOtherBinding
import id.outivox.movo.presentation.detail.DetailActivity
import id.outivox.movo.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtherFragment : Fragment() {

    private var _binding: FragmentOtherBinding? = null
    private val binding get() = _binding as FragmentOtherBinding

    private val viewModel: DetailViewModel by viewModel()
    private val horizontalReviewAdapter by lazy { ReviewAdapter() }
    private val horizontalSimilarAdapter by lazy { HorizontalListAdapter(::onItemClick) }
    private val horizontalRecommendedAdapter by lazy { HorizontalListAdapter(::onItemClick) }

    private val mediaId by lazy { arguments?.getInt(BUNDLE_MOVIE_ID).orZero() }
    private val isMovie by lazy { arguments?.getString(BUNDLE_MEDIA_TYPE).equals(BUNDLE_MEDIA_MOVIE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initData()
        initView()
        initAction()
    }

    private fun initObservers() {
        viewModel.apply {
            if (isMovie) {
                movieSimilar.observe(viewLifecycleOwner, ::setupSimilarMoviesData)
                movieRecommendations.observe(viewLifecycleOwner, ::setupRecommendationsMoviesData)
                movieReviews.observe(viewLifecycleOwner, ::setupReviewsMovieData)
            } else {
                tvSimilar.observe(viewLifecycleOwner, ::setupSimilarTvData)
                tvRecommendations.observe(viewLifecycleOwner, ::setupRecommendationsTvData)
                tvReviews.observe(viewLifecycleOwner, ::setupReviewsTvData)
            }
            isLoading.observe(viewLifecycleOwner, ::setupLoadingData)
        }
    }

    private fun initData() {
        viewModel.apply {
            if (isMovie) {
                getSimilarMovies(mediaId)
                getMovieReviews(mediaId)
                getRecommendationsMovies(mediaId)
            } else {
                getSimilarTv(mediaId)
                getTvReviews(mediaId)
                getRecommendationsTv(mediaId)
            }
        }
    }

    private fun initView() {
        with(binding) {
            rvReviews.apply {
                adapter = horizontalReviewAdapter.withLoadStateFooter(
                    footer = MovieLoadStateAdapter { horizontalReviewAdapter.retry() }
                )

                horizontalReviewAdapter.addLoadStateListener {
                    val state = it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && horizontalReviewAdapter.itemCount == 0
                    isInvisible = state
                    tvNoReviews.isVisible = state
                }
            }

            rvSimilar.apply {
                adapter = horizontalSimilarAdapter.withLoadStateFooter(
                    footer = MovieLoadStateAdapter { horizontalSimilarAdapter.retry() }
                )

                horizontalSimilarAdapter.addLoadStateListener {
                    val state = it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && horizontalSimilarAdapter.itemCount == 0
                    isInvisible = state
                    tvNoSimilarMovies.isVisible = state
                }
            }

            rvRecommended.apply {
                adapter = horizontalRecommendedAdapter.withLoadStateFooter(
                    footer = MovieLoadStateAdapter { horizontalRecommendedAdapter.retry() }
                )

                horizontalRecommendedAdapter.addLoadStateListener {
                    val state = it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && horizontalRecommendedAdapter.itemCount == 0
                    isInvisible = state
                    tvNoRecommendedMovies.isVisible = state
                }
            }
        }
    }

    private fun initAction() = binding.apply {
        tvSeeAllSimilar.setOnClickListener { requireContext().getString(R.string.under_development).showSnackbar(root) }
        tvSeeAllRecommended.setOnClickListener { requireContext().getString(R.string.under_development).showSnackbar(root) }
    }

    private fun setupLoadingData(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setupReviewsTvData(resource: Resource<PagingData<Review>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data = resource.data.map { it }
                horizontalReviewAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvReviews.visibility = View.INVISIBLE
                    tvNoReviews.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupRecommendationsTvData(resource: Resource<PagingData<Tv>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                horizontalRecommendedAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvRecommended.visibility = View.INVISIBLE
                    tvNoRecommendedMovies.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupSimilarTvData(resource: Resource<PagingData<Tv>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                horizontalSimilarAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvSimilar.visibility = View.INVISIBLE
                    tvNoSimilarMovies.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupReviewsMovieData(resource: Resource<PagingData<Review>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data = resource.data.map { it }
                horizontalReviewAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvReviews.visibility = View.INVISIBLE
                    tvNoReviews.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupRecommendationsMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                horizontalRecommendedAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvRecommended.visibility = View.INVISIBLE
                    tvNoRecommendedMovies.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupSimilarMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                horizontalSimilarAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvSimilar.visibility = View.INVISIBLE
                    tvNoSimilarMovies.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

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