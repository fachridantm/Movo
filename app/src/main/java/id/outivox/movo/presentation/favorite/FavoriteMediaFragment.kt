package id.outivox.movo.presentation.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.MOVIE
import id.outivox.core.utils.Constants.TV_SHOW
import id.outivox.core.utils.toJson
import id.outivox.movo.R
import id.outivox.movo.databinding.FragmentTabMediaBinding
import id.outivox.movo.presentation.detail.DetailActivity
import id.outivox.movo.presentation.favorite.adapter.FavoriteAdapter
import id.outivox.movo.presentation.home.HomeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMediaFragment : Fragment() {

    private var _binding: FragmentTabMediaBinding? = null
    private val binding get() = _binding as FragmentTabMediaBinding

    private val viewModel: FavoriteViewModel by viewModel()
    private val favoriteAdapter by lazy { FavoriteAdapter(::onItemClick) }
    private val mediaType by lazy { arguments?.getString(BUNDLE_MEDIA_TYPE).orEmpty() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTabMediaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initView()
    }

    private fun initObservers() {
        if (mediaType.equals(BUNDLE_MEDIA_MOVIE, true)) viewModel.favoriteListMovies.observe(viewLifecycleOwner, ::setupMoviesData)
        else viewModel.favoriteListTv.observe(viewLifecycleOwner, ::setupTvShowData)
    }

    private fun initData() {
        if (mediaType.equals(BUNDLE_MEDIA_MOVIE, true)) viewModel.getFavoriteMovies() else viewModel.getFavoriteTv()
    }

    private fun initView() {
        binding.rvResults.apply {
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupMoviesData(movies: List<MovieDetail>?) {
        if (movies.isNullOrEmpty()) {
            binding.apply {
                rvResults.visibility = View.INVISIBLE
                tvNoResults.apply {
                    text = requireContext().getString(R.string.no_favorite_movies)
                    visibility = View.VISIBLE
                }
            }
        } else {
            favoriteAdapter.submitList(movies)
        }
        Log.i("logInfo", "setupMoviesData: ${movies.toJson()}")
    }

    private fun setupTvShowData(tvs: List<TvDetail>?) {
        if (tvs.isNullOrEmpty()) {
            binding.apply {
                rvResults.visibility = View.INVISIBLE
                tvNoResults.apply {
                    text = requireContext().getString(R.string.no_favorite_tv)
                    visibility = View.VISIBLE
                }
            }
        } else {
            favoriteAdapter.submitList(tvs)
        }
        Log.i("logInfo", "setupTvShowData: ${tvs.toJson()}")
    }

    private fun onItemClick(any: Any) {
        when (any) {
            is MovieDetail -> findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(any.id, MOVIE))
            is TvDetail -> findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(any.id, TV_SHOW))
        }
    }

    override fun onResume() {
        super.onResume()
        initData()
    }
}