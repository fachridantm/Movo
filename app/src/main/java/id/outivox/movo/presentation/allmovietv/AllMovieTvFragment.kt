package id.outivox.movo.presentation.allmovietv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.paging.map
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.Constants.MOVIE
import id.outivox.core.utils.Constants.TV_SHOW
import id.outivox.core.utils.toCategoryTitle
import id.outivox.movo.adapter.VerticalListAdapter
import id.outivox.movo.databinding.FragmentAllMovieTvBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMovieTvFragment : Fragment() {

    private var _binding: FragmentAllMovieTvBinding? = null
    private val binding get() = _binding as FragmentAllMovieTvBinding

    private val viewModel: AllMovieTvViewModel by viewModel()
    private val verticalAdapter by lazy { VerticalListAdapter(::onItemClick) }

    private val args: AllMovieTvFragmentArgs by navArgs()

    private val category by lazy { args.category }
    private val isMovie by lazy { args.mediaType == MOVIE }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAllMovieTvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initData()
        initView()
    }

    private fun initObservers() {
        viewModel.apply {
            movies.observe(viewLifecycleOwner, ::setupMoviesData)
            tvShow.observe(viewLifecycleOwner, ::setupTvShowData)
            isLoading.observe(viewLifecycleOwner, ::setupLoadingData)
        }
    }

    private fun initData() {
        viewModel.apply {
            if (isMovie) getMoviesByCategory(category)
            else getTvShowByCategory(category)
        }
    }

    private fun initView() {
        with(binding) {
            toolbar.apply {
                title = category.toCategoryTitle()
                setNavigationOnClickListener { findNavController().popBackStack() }
            }
        }
    }

    private fun setupLoadingData(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setupMoviesData(resource: Resource<PagingData<Movie>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                verticalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvHome.visibility = View.INVISIBLE
                    tvDataIsEmpty.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }

    private fun setupTvShowData(resource: Resource<PagingData<Tv>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data: PagingData<Any> = resource.data.map { it }
                verticalAdapter.submitData(lifecycle, data)
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvHome.visibility = View.INVISIBLE
                    tvDataIsEmpty.apply {
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
            is Movie -> findNavController().navigate(AllMovieTvFragmentDirections.actionAllMovieTvFragmentToDetailActivity(any.id, MOVIE))
            is Tv -> findNavController().navigate(AllMovieTvFragmentDirections.actionAllMovieTvFragmentToDetailActivity(any.id, TV_SHOW))
        }
    }
}