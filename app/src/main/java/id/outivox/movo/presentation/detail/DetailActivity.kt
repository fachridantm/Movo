package id.outivox.movo.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.utils.Constants.EXTRA_DETAIL_ID
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TYPE
import id.outivox.core.utils.Constants.MOVIE
import id.outivox.core.utils.loadImageOnly
import id.outivox.core.utils.orFalse
import id.outivox.core.utils.orZero
import id.outivox.core.utils.reformatDate
import id.outivox.core.utils.setTransparentStatusBar
import id.outivox.core.utils.showSnackbar
import id.outivox.core.utils.toJson
import id.outivox.core.utils.toMovieDurationFormat
import id.outivox.movo.R
import id.outivox.movo.databinding.ActivityDetailBinding
import id.outivox.movo.presentation.detail.fragments.adapter.DetailAdapter
import id.outivox.movo.presentation.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModel()
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private val args: DetailActivityArgs by navArgs()

    private val argsId by lazy { args.mediaId.orZero() }
    private val argsMediaType by lazy { args.mediaType }

    private val intentId by lazy { intent.getIntExtra(EXTRA_DETAIL_ID, 0) }
    private val intentMediaType by lazy { intent.getStringExtra(EXTRA_MEDIA_TYPE).orEmpty() }

    private lateinit var movieData: MovieDetail
    private lateinit var tvData: TvDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        initData()
        initView()
    }

    private fun initObservers() {
        favoriteViewModel.apply {
            favoriteListMovies.observe(this@DetailActivity, ::setupFavoriteMovies)
            favoriteListTv.observe(this@DetailActivity, ::setupFavoriteTv)
            snackbar.observe(this@DetailActivity) { it.getContentIfNotHandled()?.showSnackbar(binding.root) }
        }

        detailViewModel.apply {
            movieDetail.observe(this@DetailActivity, ::setupMovieDetail)
            tvDetail.observe(this@DetailActivity, ::setupTvDetail)
        }
    }

    private fun initData() {
        val mediaId = if (intentId != 0) intentId else argsId
        val mediaType = if (intentMediaType.isNotEmpty()) intentMediaType else argsMediaType
        if (mediaType == MOVIE) {
            detailViewModel.getMovieDetail(mediaId)
            favoriteViewModel.getFavoriteMovies()
        } else {
            detailViewModel.getTvDetail(mediaId)
            favoriteViewModel.getFavoriteTv()
        }
    }

    private fun initView() {
        with(binding) {
            toolbarDetail.apply {
                setTransparentStatusBar()
                setSupportActionBar(this)
                setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
            }

            val mediaId = if (intentId != 0) intentId else argsId
            val data = if (::movieData.isInitialized) movieData else if (::tvData.isInitialized) tvData else null
            vpDetail.adapter = DetailAdapter(this@DetailActivity, mediaId, data)

            TabLayoutMediator(tabDetail, vpDetail) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.title_overview)
                    1 -> tab.text = getString(R.string.title_others)
                }
            }.attach()
        }
    }

    private fun setupMovieDetail(resource: Resource<MovieDetail>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data = resource.data
                movieData = data
                binding.apply {
                    ivPoster.loadImageOnly(data.posterPath)
                    tvTitle.text = data.title
                    collapsingToolbarDetail.title = data.title
                    tvRatingCount.text = data.voteAverage.toString().take(3)
                    tvRatersCount.text = getString(R.string.vote_count, data.voteCount)
                    tvReleaseData.text = data.releaseDate.reformatDate()
                    tvDurationOrEpisode.text = data.duration.toMovieDurationFormat()
                }
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupTvDetail(resource: Resource<TvDetail>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data = resource.data
                tvData = data
                binding.apply {
                    ivPoster.loadImageOnly(data.posterPath)
                    tvTitle.text = data.title
                    collapsingToolbarDetail.title = data.title
                    tvRatingCount.text = data.voteAverage.toString().take(3)
                    tvRatersCount.text = getString(R.string.vote_count, data.voteCount)
                    tvReleaseData.text = data.firstAirDate.reformatDate()
                    tvDurationOrEpisode.text = getString(R.string.eps_and_seasons, data.numberOfEpisodes, data.numberOfSeasons)
                }
            }

            is Resource.Error -> resource.message.showSnackbar(binding.root)

            else -> {}
        }
    }

    private fun setupFavoriteMovies(list: List<MovieDetail>?) {
        list.let { movies ->
            val data = if (::movieData.isInitialized) movieData else null
            val isFavorite = movies?.any { favorite -> favorite.id == data?.id }.orFalse()
            if (data != null) {
                setFavoriteIcon(isFavorite)
                setFavoriteIconOnClickListener(isFavorite, data)
            } else {
                initData()
            }
        }
    }

    private fun setupFavoriteTv(list: List<TvDetail>?) {
        list.let { tvs ->
            val data = if (::tvData.isInitialized) tvData else null
            val isFavorite = tvs?.any { favorite -> favorite.id == data?.id }.orFalse()
            if (data != null) {
                setFavoriteIcon(isFavorite)
                setFavoriteIconOnClickListener(isFavorite, data)
            } else {
                initData()
            }
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        binding.tvFavorite.setCompoundDrawablesWithIntrinsicBounds(
            if (isFavorite) R.drawable.ic_favorite_active else R.drawable.ic_favorite,
            0, 0, 0
        )
    }

    private fun <T> setFavoriteIconOnClickListener(isFavorite: Boolean, data: T) {
        binding.tvFavorite.setOnClickListener {
            favoriteViewModel.apply {
                Log.i("logInfo", "setFavoriteIconOnClickListener: ${data.toJson()}")
                val tvDetail = data as? TvDetail
                val movieDetail = data as? MovieDetail
                val mediaType = if (intentMediaType.isNotEmpty()) intentMediaType else argsMediaType
                if (mediaType == MOVIE) {
                    if (movieDetail != null) {
                        setFavoriteMovie(movieDetail, !isFavorite, this@DetailActivity)
                        Log.i("logInfo", "setFavoriteMovie: ${movieDetail.toJson()}")
                    }
                } else {
                    if (tvDetail != null) {
                        setFavoriteTv(tvDetail, !isFavorite, this@DetailActivity)
                        Log.i("logInfo", "setFavoriteTv: ${tvDetail.toJson()}")
                    }
                }
            }
            setFavoriteIcon(!isFavorite)
        }
    }

    companion object {
        fun start(
            context: Context,
            id: Int,
            mediaType: String,
        ) = Intent(context, DetailActivity::class.java).apply {
            putExtra(EXTRA_DETAIL_ID, id)
            putExtra(EXTRA_MEDIA_TYPE, mediaType)
        }.run { context.startActivity(this) }
    }
}