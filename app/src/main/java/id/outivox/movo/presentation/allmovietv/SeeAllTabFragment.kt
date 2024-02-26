package id.outivox.movo.presentation.allmovietv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_CATEGORY
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_PAGE
import id.outivox.core.utils.Constants.EXTRA_DETAIL_ID
import id.outivox.core.utils.Constants.NOW_PLAYING_MOVIE
import id.outivox.movo.adapter.VerticalListAdapter
import id.outivox.movo.databinding.FragmentSeeAllTabBinding
import id.outivox.movo.`interface`.OnItemClickCallback
import id.outivox.movo.presentation.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeeAllTabFragment : Fragment() {

    private val viewModel: AllMovieTvViewModel by viewModel()

    private var _binding: FragmentSeeAllTabBinding? = null
    private val binding get() = _binding as FragmentSeeAllTabBinding

    private lateinit var category: String
    private var page = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeAllTabBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category = arguments?.getString(BUNDLE_MOVIE_CATEGORY) ?: NOW_PLAYING_MOVIE
        page = arguments?.getInt(BUNDLE_MOVIE_PAGE) ?: 1

        initObservers()
    }

    private fun initObservers() {
        viewModel.getMoviesByCategory(category, page.toString())
        viewModel.movieResponse.observe(viewLifecycleOwner) {
            setUpHomeTabRv(it)
        }
    }

    private fun <T> setUpHomeTabRv(resource: Resource<T>) {
        when (resource) {
            is Resource.Success -> {
                when (resource.data) {
                    is MovieResult -> {
                        val result = resource.data as MovieResult
                        binding.rvSeeAllTab.apply {
                            val mAdapter = VerticalListAdapter<Movie>()
                            adapter = mAdapter
                            mAdapter.setData(result.movie)

                            mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                                override fun onItemClicked(id: Int) {
                                    startActivity(
                                        Intent(context, DetailActivity::class.java)
                                            .putExtra(EXTRA_DETAIL_ID, id)
                                    )
                                }
                            })
                        }
                    }
                }
            }

            else -> {}
        }

    }
}