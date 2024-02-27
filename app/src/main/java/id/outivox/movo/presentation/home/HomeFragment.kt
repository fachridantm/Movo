package id.outivox.movo.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
import id.outivox.movo.presentation.detail.DetailActivity
import id.outivox.movo.presentation.home.component.CenterItemLayoutManager
import id.outivox.movo.presentation.home.fragment.adapter.HomeCategoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()
    private val carouselAdapter: CarouselAdapter by lazy { CarouselAdapter(::onCarouselClick) }
    private val genreListAdapter: GenreListAdapter by lazy { GenreListAdapter() }
    private val horizontalAdapter: HorizontalListAdapter by lazy { HorizontalListAdapter(::onItemHorizontalClick) }
    private val homeCategoryAdapter: HomeCategoryAdapter by lazy { HomeCategoryAdapter(requireActivity()) }

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

        initData()
        initObserver()
        initView()
        initListener()
    }

    private fun initData() {
        viewModel.apply {
            getNowPlayingMovies()
            getPopularMovies()
            getUpcomingMovies()
            getAiringTodayTv()
            getPopularTv()
        }
    }

    private fun initObserver() {
        viewModel.apply {
            nowPlayingMovies.observe(viewLifecycleOwner, ::setupNowPlayingMoviesData)
            popularMovies.observe(viewLifecycleOwner, ::setupPopularMoviesData)
            upcomingMovies.observe(viewLifecycleOwner, ::setupUpcomingMoviesData)
            airingTodayTv.observe(viewLifecycleOwner, ::setupAiringTodayTvData)
            popularTv.observe(viewLifecycleOwner, ::setupPopularTvData)
        }
    }

    private fun initView() {
        binding.apply {
            rvCarousel.apply {
                adapter = carouselAdapter.withLoadStateFooter(
                    footer = MovieLoadStateAdapter { carouselAdapter.retry() }
                )
                onFlingListener = null
                layoutManager = CenterItemLayoutManager(context, RecyclerView.HORIZONTAL, false)
                PagerSnapHelper().attachToRecyclerView(this)
            }

            vpMovies.apply {
                adapter = homeCategoryAdapter
                TabLayoutMediator(tabLayout, this) { tab, position ->
                    val listTab = listOf(AIRING_TODAY_TV, TOP_RATED_MOVIE, TOP_RATED_TV, POPULAR_TV)
                    tab.text = listTab[position].toCategoryTitle()
                }.attach()
            }

            rvCarouselGenre.adapter = genreListAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter { carouselAdapter.retry() }
            )
        }
    }


    private fun setupNowPlayingMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data = resource.data
//                carouselAdapter.submitData(lifecycle, data)
                genreListAdapter.submitData(lifecycle, data.map { it.genres })

            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupPopularMoviesData(resource: Resource<PagingData<Movie>>?) {
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

    private fun setupUpcomingMoviesData(resource: Resource<PagingData<Movie>>?) {
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

    private fun setupAiringTodayTvData(resource: Resource<PagingData<Tv>>?) {
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

    private fun setupPopularTvData(resource: Resource<PagingData<Tv>>?) {
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

    private fun initListener() {
        binding.apply {
            svHomeClick.setOnClickListener {
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                )
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
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAllMovieTvFragment(
                        category = POPULAR_MOVIE
                    )
                )

            }

            tvSeeAllUpcomingMovies.setOnClickListener {
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAllMovieTvFragment(
                        category = UPCOMING_MOVIE
                    )
                )
            }
        }
    }

    private fun onCarouselClick(movie: Movie) {
        DetailActivity.start(requireActivity(), movie.id, MOVIE)
    }

    private fun onItemHorizontalClick(any: Any) {
        val movie = any as Movie
        val tv = any as Tv
        when (any) {
            is Movie -> DetailActivity.start(requireActivity(), movie.id, MOVIE)
            is Tv -> DetailActivity.start(requireActivity(), tv.id, TV_SHOW)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
