package id.outivox.movo.presentation.allmovietv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.paging.map
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.Constants
import id.outivox.core.utils.showSnackbar
import id.outivox.core.utils.toCategoryTitle
import id.outivox.movo.R
import id.outivox.movo.adapter.VerticalListAdapter
import id.outivox.movo.databinding.FragmentAllMovieTvBinding
import id.outivox.movo.presentation.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMovieTvFragment : Fragment() {

    private var _binding: FragmentAllMovieTvBinding? = null
    private val binding get() = _binding as FragmentAllMovieTvBinding

    private val viewModel: AllMovieTvViewModel by viewModel()
    private val verticalAdapter by lazy { VerticalListAdapter(::onItemClick) }

    private lateinit var category: String

    private val args: AllMovieTvFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAllMovieTvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category = args.category

        initData()
        initObservers()
        initView()
    }

    private fun initData() {
        viewModel.apply {
            getMoviesByCategory(category)
            getTvShowByCategory(category)
        }
    }

    private fun initObservers() {
        viewModel.apply {
            movies.observe(viewLifecycleOwner, ::setupMoviesData)
            tvShow.observe(viewLifecycleOwner, ::setupTvShowData)
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
            is Movie -> DetailActivity.start(requireContext(), any.id, Constants.MOVIE)
            is Tv -> DetailActivity.start(requireContext(), any.id, Constants.TV_SHOW)
        }
    }

    private fun initView() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvMovieTvCategory.text = category.toCategoryTitle()
        }
    }
}