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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.Actor
import id.outivox.core.domain.model.detail.Video
import id.outivox.core.domain.model.detail.Wallpaper
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_ID
import id.outivox.core.utils.orZero
import id.outivox.movo.adapter.ActorAdapter
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

    private val mediaId by lazy { arguments?.getInt(BUNDLE_MOVIE_ID).orZero() }
    private val isMovie by lazy { arguments?.getString(BUNDLE_MEDIA_TYPE).equals(BUNDLE_MEDIA_MOVIE) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOverviewBinding.inflate(layoutInflater)
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
            if (isMovie) {
                movieActor.observe(viewLifecycleOwner, ::setupMovieTvActorData)
                movieWallpaper.observe(viewLifecycleOwner, ::setupMovieTvWallpaperData)
                movieTrailer.observe(viewLifecycleOwner, ::setupMovieTvTrailerData)
            } else {
                tvActor.observe(viewLifecycleOwner, ::setupMovieTvActorData)
                tvWallpaper.observe(viewLifecycleOwner, ::setupMovieTvWallpaperData)
                tvTrailer.observe(viewLifecycleOwner, ::setupMovieTvTrailerData)
            }
            isLoading.observe(viewLifecycleOwner, ::setupLoadingData)
        }
    }

    private fun initData() {
        viewModel.apply {
            if (isMovie) {
                getMovieActor(mediaId)
                getMovieWallpaper(mediaId)
                getMovieTrailer(mediaId)
            } else {
                getTvActor(mediaId)
                getTvWallpaper(mediaId)
                getTvTrailer(mediaId)
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

    private fun setupLoadingData(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupMovieTvTrailerData(resource: Resource<List<Video>>) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                if (resource.data.isEmpty()) {
                    binding.wvTrailer.visibility = View.INVISIBLE
                    binding.tvTrailerIsEmpty.visibility = View.VISIBLE
                } else {
                    val frameVideo = "<html><body style=\"margin: 0;margin-top: -30px;\"><br><iframe  width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${
                        resource.data[0].key
                    }\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
                    binding.wvTrailer.loadData(frameVideo, "text/html", "utf-8")
                }
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.wvTrailer.visibility = View.INVISIBLE
                binding.tvTrailerIsEmpty.apply {
                    text = resource.message
                    visibility = View.VISIBLE
                }
            }

            else -> {}
        }
    }

    private fun setupMovieTvWallpaperData(resource: Resource<Wallpaper>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data = listOf(resource.data)
                if (data.isNotEmpty()) wallpaperAdapter.submitList(data)
                else {
                    binding.apply {
                        rvWallpapers.visibility = View.INVISIBLE
                        tvWallpaperIsEmpty.visibility = View.VISIBLE
                    }
                }
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvWallpapers.visibility = View.INVISIBLE
                    tvWallpaperIsEmpty.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }

    }

    private fun setupMovieTvActorData(resource: Resource<List<Actor>>?) {
        when (resource) {
            is Resource.Loading -> viewModel.isLoading.value = true

            is Resource.Success -> {
                viewModel.isLoading.value = false
                val data = resource.data
                if (data.isNotEmpty()) actorAdapter.submitList(data)
                else {
                    binding.apply {
                        rvActors.visibility = View.INVISIBLE
                        tvActorIsEmpty.visibility = View.VISIBLE
                    }
                }
            }

            is Resource.Error -> {
                viewModel.isLoading.value = false
                binding.apply {
                    rvActors.visibility = View.INVISIBLE
                    tvActorIsEmpty.apply {
                        text = resource.message
                        visibility = View.VISIBLE
                    }
                }
            }

            else -> {}
        }
    }
}