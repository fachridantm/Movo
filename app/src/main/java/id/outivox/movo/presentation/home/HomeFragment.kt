package id.outivox.movo.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.Constants.AIRING_TODAY_TV
import id.outivox.core.utils.Constants.MOVIE
import id.outivox.core.utils.Constants.POPULAR_MOVIE
import id.outivox.core.utils.Constants.POPULAR_TV
import id.outivox.core.utils.Constants.TOP_RATED_MOVIE
import id.outivox.core.utils.Constants.TOP_RATED_TV
import id.outivox.core.utils.Constants.TV_SHOW
import id.outivox.core.utils.Constants.UPCOMING_MOVIE
import id.outivox.core.utils.showSnackbar
import id.outivox.core.utils.toCategoryTitle
import id.outivox.movo.R
import id.outivox.movo.adapter.CarouselAdapter
import id.outivox.movo.adapter.GenreListAdapter
import id.outivox.movo.adapter.HorizontalListAdapter
import id.outivox.movo.adapter.MovieLoadStateAdapter
import id.outivox.movo.databinding.FragmentHomeBinding
import id.outivox.movo.presentation.home.component.CenterItemLayoutManager
import id.outivox.movo.presentation.home.fragment.adapter.HomeCategoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()
    private val carouselAdapter: CarouselAdapter by lazy { CarouselAdapter(::onCarouselClick) }
    private val genreListAdapter: GenreListAdapter by lazy { GenreListAdapter() }
    private val horizontalPopularMovieAdapter: HorizontalListAdapter by lazy { HorizontalListAdapter(::onItemHorizontalClick) }
    private val horizontaUpcomingMovieAdapter: HorizontalListAdapter by lazy { HorizontalListAdapter(::onItemHorizontalClick) }

    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initData()
        initView()
        initListener()
    }

    private fun initObservers() {
        viewModel.apply {
            nowPlayingMovies.observe(viewLifecycleOwner, ::setupNowPlayingMoviesData)
            popularMovies.observe(viewLifecycleOwner, ::setupPopularMoviesData)
            upcomingMovies.observe(viewLifecycleOwner, ::setupUpcomingMoviesData)
            isLoading.observe(viewLifecycleOwner, ::setupLoadingData)
        }
    }

    private fun initData() {
        viewModel.apply {
            getNowPlayingMovies()
            getPopularMovies()
            getUpcomingMovies()
        }
    }

    private fun initView() {
        binding.apply {
            rvCarouselGenre.adapter = genreListAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { carouselAdapter.retry() }
            )

            rvCarousel.apply {
                adapter = carouselAdapter.withLoadStateFooter(
                    footer = MovieLoadStateAdapter { carouselAdapter.retry() }
                )
                onFlingListener = null
                layoutManager = CenterItemLayoutManager(context, RecyclerView.HORIZONTAL, false)
                PagerSnapHelper().attachToRecyclerView(this)

                carouselAdapter.addLoadStateListener { loadState ->
                    val state = loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && carouselAdapter.itemCount == 0
                    listOf(
                        rvCarousel,
                        rvCarouselGenre
                    ).forEach { it.isInvisible = state }
                    tvEmptyNowPlaying.isVisible = state
                }
            }

            vpMovies.apply {
                adapter = HomeCategoryAdapter(requireActivity())
                TabLayoutMediator(tabLayout, vpMovies) { tab, position ->
                    val listTab = listOf(AIRING_TODAY_TV, TOP_RATED_MOVIE, TOP_RATED_TV, POPULAR_TV)
                    tab.text = listTab[position].toCategoryTitle()
                }.attach()
            }

            rvPopularMovies.adapter = horizontalPopularMovieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { horizontalPopularMovieAdapter.retry() }
            )

            horizontalPopularMovieAdapter.addLoadStateListener {
                val state = it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && horizontalPopularMovieAdapter.itemCount == 0
                rvPopularMovies.isInvisible = state
                tvEmptyPopularMovies.isVisible = state
            }

            rvUpcomingMovies.adapter = horizontaUpcomingMovieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { horizontaUpcomingMovieAdapter.retry() }
            )

            horizontaUpcomingMovieAdapter.addLoadStateListener {
                val state = it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && horizontaUpcomingMovieAdapter.itemCount == 0
                rvUpcomingMovies.isInvisible = state
                tvEmptyUpcomingMovies.isVisible = state
            }
        }
    }

    private fun setupLoadingData(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setupNowPlayingMoviesData(resource: Resource<PagingData<Movie>>?) {
        Log.i("logInfo", "setupNowPlayingMoviesData: $resource")
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data = resource.data
                carouselAdapter.submitData(lifecycle, data)
                genreListAdapter.submitData(lifecycle, data.map { it.genres })
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    listOf(
                        rvCarousel,
                        rvCarouselGenre
                    ).forEach { it.visibility = View.GONE }
                    tvEmptyNowPlaying.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupPopularMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                horizontalPopularMovieAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvPopularMovies.visibility = View.INVISIBLE
                    tvEmptyPopularMovies.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupUpcomingMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                horizontaUpcomingMovieAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvUpcomingMovies.visibility = View.INVISIBLE
                    tvEmptyUpcomingMovies.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun initListener() {
        binding.apply {
            svHomeClick.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }

            tvRegion.setOnClickListener {
                // TODO: Add region picker
                getString(R.string.under_development).showSnackbar(binding.root)
            }

            rvCarousel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        try {

                        } catch (e: UninitializedPropertyAccessException) {
                            e.printStackTrace()
                            Log.e("logError", e.message.orEmpty())
                        }
                    }
                }
            })

            tvSeeAllPopularMovies.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllMovieTvFragment(category = POPULAR_MOVIE))
            }

            tvSeeAllUpcomingMovies.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllMovieTvFragment(category = UPCOMING_MOVIE))
            }
        }
    }

    private fun onCarouselClick(movie: Movie) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailActivity(movie.id, MOVIE))
    }

    private fun onItemHorizontalClick(any: Any) {
        when (any) {
            is Movie -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailActivity(any.id, MOVIE))
            is Tv -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailActivity(any.id, TV_SHOW))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
