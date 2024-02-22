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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_DETAIL
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_ID
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.*
import id.outivox.movo.adapter.ActorAdapter
import id.outivox.movo.adapter.WallpaperAdapter
import id.outivox.movo.databinding.FragmentOverviewBinding
import id.outivox.movo.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding as FragmentOverviewBinding

    private var _movieDetail: MovieDetail? = null
    private val movieDetail get() = _movieDetail as MovieDetail

    private var _tvDetail: TvDetail? = null
    private val tvDetail get() = _tvDetail as TvDetail

    private val viewModel: DetailViewModel by viewModel()

    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(layoutInflater)

        id = arguments?.getString(BUNDLE_MOVIE_ID) ?: "0"

        initObserver()
        setUpView()

        return binding.root
    }

    private fun setUpView() {
        if(isMovieType()){
            _movieDetail = arguments?.getParcelable(BUNDLE_MOVIE_DETAIL)
            binding.tvSynopsis.text = movieDetail.overview
        } else {
            _tvDetail = arguments?.getParcelable(BUNDLE_MOVIE_DETAIL)
            binding.tvSynopsis.text = tvDetail.overview
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setTrailerWebView(resource: Resource<List<Video>>) {
        binding.apply {
            when (resource) {
                is Resource.Success -> {
                    if (resource.data?.isEmpty() != true) {
                        val frameVideo = "<html><body style=\"margin: 0;margin-top: -30px;\"><br><iframe  width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${
                            resource.data?.get(
                                0
                            )?.key
                        }\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

                        webViewTrailer.apply {
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
                            loadData(frameVideo, "text/html", "utf-8")
                        }
                    }

                }
                else -> {}
            }
        }
    }

    private fun initObserver() {
        if(isMovieType()){
            viewModel.getMovieActor(id)
            viewModel.actorResponse.observe(viewLifecycleOwner) {
                setUpActorData(it)
            }

            viewModel.getMovieWallpaper(id)
            viewModel.wallpaperResponse.observe(viewLifecycleOwner) {
                setUpWallpaperData(it)
            }

            viewModel.getMovieTrailer(id)
            viewModel.videoResponse.observe(viewLifecycleOwner) {
                setTrailerWebView(it)
            }
        } else {
            viewModel.getTvActor(id)
            viewModel.actorResponse.observe(viewLifecycleOwner) {
                setUpActorData(it)
            }

            viewModel.getTvTrailer(id)
            viewModel.wallpaperResponse.observe(viewLifecycleOwner) {
                setUpWallpaperData(it)
            }

            viewModel.getTvTrailer(id)
            viewModel.videoResponse.observe(viewLifecycleOwner) {
                setTrailerWebView(it)
            }
        }
    }

    private fun setUpWallpaperData(resource: Resource<Wallpaper>?) {
        when (resource) {
            is Resource.Success -> {
                binding.rvWallpaper.apply {
                    val mAdapter = WallpaperAdapter()
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter = mAdapter
                    resource.data?.wallpaperUrl?.let { mAdapter.setData(it) }
                }
            }
            else -> {}
        }

    }

    private fun setUpActorData(resource: Resource<List<Actor>>?) {
        when (resource) {
            is Resource.Success -> {
                binding.rvActors.apply {
                    val mAdapter = ActorAdapter()
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    adapter = mAdapter
                    mAdapter.setData(resource.data)
                }
            }
            else -> {}
        }

    }

    private fun isMovieType(): Boolean {
        return when (arguments?.getString(BUNDLE_MEDIA_TYPE)) {
            BUNDLE_MEDIA_MOVIE -> {
                true
            }
            BUNDLE_MEDIA_TV -> {
                false
            }
            else -> {
                true
            }
        }
    }
}