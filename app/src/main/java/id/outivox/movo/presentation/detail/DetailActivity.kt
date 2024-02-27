package id.outivox.movo.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.utils.Constants.EXTRA_DETAIL_ID
import id.outivox.core.utils.Constants.EXTRA_MEDIA_MOVIE
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TYPE
import id.outivox.core.utils.loadImageOnly
import id.outivox.core.utils.orFalse
import id.outivox.core.utils.reformatDate
import id.outivox.core.utils.setTransparentStatusBar
import id.outivox.core.utils.showSnackbar
import id.outivox.core.utils.toMovieDurationFormat
import id.outivox.movo.R
import id.outivox.movo.databinding.ActivityDetailBinding
import id.outivox.movo.presentation.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModel()
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var id: Int = 0
    private var isMovie: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTransparentStatusBar()
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getIntExtra(EXTRA_DETAIL_ID, 0)
        isMovie = intent.getStringExtra(EXTRA_MEDIA_TYPE).equals(EXTRA_MEDIA_MOVIE, true)

        initData()
        initObserver()
        initView()
    }

    private fun initData() {
        if (isMovie) {
            detailViewModel.getMovieDetail(id)
            favoriteViewModel.getFavoriteMovies()
        } else {
            detailViewModel.getTvDetail(id)
            favoriteViewModel.getFavoriteTv()
        }
    }

    private fun initView() {
        with(binding) {
            toolbarDetail.apply {
                setNavigationIcon(R.drawable.ic_back)
                setSupportActionBar(this)
            }


            TabLayoutMediator(tabDetail, vpDetail) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.title_overview)
                    1 -> tab.text = getString(R.string.title_others)
                }
            }.attach()
        }
    }

    private fun initObserver() {
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

    private fun setupMovieDetail(resource: Resource<MovieDetail>?) {
        when (resource) {
            is Resource.Loading -> {}

            is Resource.Success -> {
                val data = resource.data
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
            val isFavorite = movies?.any { favorite -> favorite.id == id }.orFalse()
            setFavoriteIcon(isFavorite)
            setFavoriteIconOnClickListener(isFavorite, movies)
        }
    }

    private fun setupFavoriteTv(list: List<TvDetail>?) {
        list.let { tvs ->
            val isFavorite = tvs?.any { favorite -> favorite.id == id }.orFalse()
            setFavoriteIcon(isFavorite)
            setFavoriteIconOnClickListener(isFavorite, tvs)
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
                if (isMovie) setFavoriteMovie(data as MovieDetail, !isFavorite, this@DetailActivity)
                else setFavoriteTv(data as TvDetail, !isFavorite, this@DetailActivity)
            }
            setFavoriteIcon(!isFavorite)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
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