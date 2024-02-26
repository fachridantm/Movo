package id.outivox.movo.presentation.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.Movie
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_CATEGORY
import id.outivox.core.utils.Constants.EXTRA_MEDIA_MOVIE
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TV
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TYPE
import id.outivox.core.utils.Constants.EXTRA_DETAIL_ID
import id.outivox.core.utils.Constants.NOW_PLAYING_MOVIE
import id.outivox.movo.adapter.VerticalListAdapter
import id.outivox.movo.databinding.FragmentHomeTabBinding
import id.outivox.movo.`interface`.OnItemClickCallback
import id.outivox.movo.presentation.allmovietv.AllMovieTvViewModel
import id.outivox.movo.presentation.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeTabFragment : Fragment() {

    private val viewModel: AllMovieTvViewModel by viewModel()

    private var _binding: FragmentHomeTabBinding? = null
    private val binding get() = _binding as FragmentHomeTabBinding

    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeTabBinding.inflate(layoutInflater)

        category = arguments?.getString(BUNDLE_MOVIE_CATEGORY) ?: NOW_PLAYING_MOVIE

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        if(isMovieType()){
            viewModel.getMoviesByCategory(category)
            viewModel.movieResponse.observe(viewLifecycleOwner){
                setUpHomeTabRv(it)
            }
        } else {
            viewModel.getTvByCategory(category)
            viewModel.tvResponse.observe(viewLifecycleOwner){
                setUpHomeTabRv(it)
            }
        }
    }

    private fun <T> setUpHomeTabRv(resource: Resource<T>) {
        when(resource){
            is Resource.Success -> {
                when(resource.data){
                    is MovieResult -> {
                        val result = resource.data as MovieResult
                        displayMovie(result)

                    }
                    is TvResult -> {
                        val result = resource.data as TvResult
                        displayTv(result)
                    }
                }
            }
            else -> {}
        }

    }

    private fun displayTv(result: TvResult) {
        binding.rvHomeTab.apply {
            val mAdapter = VerticalListAdapter<Tv>()
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            mAdapter.setData(result.tv)
            mAdapter.setOnItemClickCallback(object: OnItemClickCallback{
                override fun onItemClicked(id: Int) {
                    startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(EXTRA_DETAIL_ID, id)
                            .putExtra(EXTRA_MEDIA_TYPE, EXTRA_MEDIA_TV)
                    )
                }

            })
        }
    }

    private fun displayMovie(result: MovieResult) {
        binding.rvHomeTab.apply {
            val mAdapter = VerticalListAdapter<Movie>()
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            mAdapter.setData(result.movie)
            mAdapter.setOnItemClickCallback(object: OnItemClickCallback{
                override fun onItemClicked(id: Int) {
                    startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(EXTRA_DETAIL_ID, id)
                            .putExtra(EXTRA_MEDIA_TYPE, EXTRA_MEDIA_MOVIE)
                    )
                }

            })
        }
    }

    private fun isMovieType(): Boolean {
        return arguments?.getString(BUNDLE_MEDIA_TYPE) == BUNDLE_MEDIA_MOVIE
    }

}