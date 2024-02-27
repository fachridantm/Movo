package id.outivox.movo.presentation.detail.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_ID
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.*
import id.outivox.core.utils.orZero
import id.outivox.core.utils.showSnackbar
import id.outivox.movo.R
import id.outivox.movo.adapter.ActorAdapter
import id.outivox.movo.adapter.HorizontalListAdapter
import id.outivox.movo.adapter.WallpaperAdapter
import id.outivox.movo.databinding.FragmentOverviewBinding
import id.outivox.movo.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding as FragmentOverviewBinding

    private val viewModel: DetailViewModel by viewModel()
    private val actorAdapter: ActorAdapter by lazy { ActorAdapter() }
    private val wallpaperAdapter: WallpaperAdapter by lazy { WallpaperAdapter() }

    private var id: Int = 0
    private var isMovie: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getInt(BUNDLE_MOVIE_ID).orZero()
        isMovie = arguments?.getString(BUNDLE_MEDIA_TYPE).equals(BUNDLE_MEDIA_MOVIE)

        initData()
        initObserver()
        initView()
    }

    private fun initData() {
        viewModel.apply {
            if (isMovie) {
                getMovieActor(id)
                getMovieWallpaper(id)
                getMovieTrailer(id)
            } else {
                getTvActor(id)
                getTvWallpaper(id)
                getTvTrailer(id)
            }
        }
    }

    private fun initObserver() {
        viewModel.apply {
            if (isMovie) {
                movieActor.observe(viewLifecycleOwner, ::setupMovieActorData)
                movieWallpaper.observe(viewLifecycleOwner, ::setupMovieWallpaperData)
                movieTrailer.observe(viewLifecycleOwner, ::setupMovieTrailerData)
            } else {
                tvActor.observe(viewLifecycleOwner, ::setupMovieActorData)
                tvWallpaper.observe(viewLifecycleOwner, ::setupMovieWallpaperData)
                tvTrailer.observe(viewLifecycleOwner, ::setupMovieTrailerData)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        binding.apply {
            rvActors.apply {
                adapter = actorAdapter
                setHasFixedSize(true)
            }

            rvWallpapers.apply {
                adapter = wallpaperAdapter
                setHasFixedSize(true)
            }

            wvTrailer.apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        // Only allow URLs from trusted sources
                        return request?.url?.host != "https://www.youtube.com/"
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                        // Handle JavaScript alerts here
                        return super.onJsAlert(view, url, message, result)
                    }
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupMovieTrailerData(resource: Resource<List<Video>>) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val frameVideo = "<html><body style=\"margin: 0;margin-top: -30px;\"><br><iframe  width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${
                    resource.data[0].key
                }\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
                binding.wvTrailer.loadData(frameVideo, "text/html", "utf-8")
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupMovieWallpaperData(resource: Resource<Wallpaper>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data = listOf(resource.data)
                wallpaperAdapter.submitList(data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }

    }

    private fun setupMovieActorData(resource: Resource<List<Actor>>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data = resource.data
                actorAdapter.submitList(data)
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            is Resource.Empty -> getString(R.string.data_is_empty).showSnackbar(binding.root)

            else -> {}
        }
    }
}