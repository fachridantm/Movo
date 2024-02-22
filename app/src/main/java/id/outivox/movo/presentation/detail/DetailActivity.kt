package id.outivox.movo.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.utils.Constants.EXTRA_MEDIA_MOVIE
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TV
import id.outivox.core.utils.Constants.EXTRA_MEDIA_TYPE
import id.outivox.core.utils.Constants.EXTRA_MOVIE_ID
import id.outivox.core.utils.loadImageOnly
import id.outivox.core.utils.reformat
import id.outivox.core.utils.setTransparentStatusBar
import id.outivox.core.utils.showSnackbar
import id.outivox.core.utils.toMovieDurationFormat
import id.outivox.movo.R
import id.outivox.movo.databinding.ActivityDetailBinding
import id.outivox.movo.presentation.detail.fragments.adapter.DetailViewPagerAdapter
import id.outivox.movo.presentation.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding as ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModel()
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private lateinit var dataId: String
    private lateinit var dataMovie: MovieDetail
    private lateinit var dataTv: TvDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTransparentStatusBar()
        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dataId = intent.getIntExtra(EXTRA_MOVIE_ID, 0).toString()

        initData()
        initView()
        initObserver()
    }

    private fun initData() {
        if (isMovieType()) {
            detailViewModel.getMovieDetail(dataId)
            favoriteViewModel.getFavoriteMovies()
        } else {
            detailViewModel.getTvDetail(dataId)
            favoriteViewModel.getFavoriteTv()
        }
    }

    private fun initView() {
        with(binding) {
            toolbarDetail.apply {
                setNavigationIcon(R.drawable.ic_back)
                setSupportActionBar(this)
            }

            if (isMovieType()) {
                detailViewModel.movieDetail.observe(this@DetailActivity) {
                    when (it) {
                        is Resource.Loading -> {}

                        is Resource.Success -> {
                            dataMovie = it.data
                            setupMovieDetail(it.data)
                            vpOverviewAndOther.apply {
                                adapter = DetailViewPagerAdapter(this@DetailActivity, this@DetailActivity.dataId, it.data)
                                isUserInputEnabled = false
                            }
                        }

                        is Resource.Error -> it.message.showSnackbar(binding.root)

                        else -> {}
                    }
                }
            } else {
                detailViewModel.tvDetail.observe(this@DetailActivity) {
                    when (it) {
                        is Resource.Loading -> {}

                        is Resource.Success -> {
                            dataTv = it.data
                            setUpTvDetail(it.data)
                            vpOverviewAndOther.apply {
                                adapter = DetailViewPagerAdapter(this@DetailActivity, this@DetailActivity.dataId, it.data)
                                isUserInputEnabled = false
                            }
                        }

                        is Resource.Error -> it.message.showSnackbar(binding.root)

                        else -> {}
                    }
                }
            }

            TabLayoutMediator(tabDetail, vpOverviewAndOther) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.title_overview)
                    1 -> tab.text = getString(R.string.title_others)
                }
            }.attach()
        }
    }

    private fun setupMovieDetail(data: MovieDetail) {
        binding.apply {
            with(data) {
                imgPoster.loadImageOnly(posterPath)
                tvTitle.text = title
                collapsingToolbar.title = title
                tvRatingCount.text = voteAverage.toString().take(3)
                tvRatersCount.text = getString(R.string.vote_count, voteCount)
                tvReleaseData.text = releaseDate.reformat()
                tvDurationOrEpisode.text = duration.toMovieDurationFormat()
            }
        }
    }

    private fun setUpTvDetail(data: TvDetail) {
        binding.apply {
            with(data) {
                imgPoster.loadImageOnly(posterPath)
                tvTitle.text = title
                collapsingToolbar.title = title
                tvRatingCount.text = voteAverage.toString().take(3)
                tvRatersCount.text = getString(R.string.vote_count, voteCount)
                tvReleaseData.text = firstAirDate.reformat()
                tvDurationOrEpisode.text = getString(R.string.eps_and_seasons, numberOfEpisodes, numberOfSeasons)
            }
        }
    }

    private fun initObserver() {
        favoriteViewModel.favoriteListMovies.observe(this) {
            try {
                dataMovie.let { movie ->
                    val isFavorite = it.any { favorite -> favorite.id == movie.id }
                    setFavoriteIcon(isFavorite)
                    setFavoriteIconOnClickListener(isFavorite, movie)
                }
            } catch (e: UninitializedPropertyAccessException) {
                e.printStackTrace()
                Log.e("logError", e.message.toString())
            }
        }

        favoriteViewModel.favoriteListTv.observe(this) {
            try {
                dataTv.let { tv ->
                    val isFavorite = it.any { favorite -> favorite.id == tv.id }
                    setFavoriteIcon(isFavorite)
                    setFavoriteIconOnClickListener(isFavorite, tv)
                }
            } catch (e: UninitializedPropertyAccessException) {
                e.printStackTrace()
                Log.e("logError", e.message.toString())
            }
        }

        favoriteViewModel.snackbar.observe(this@DetailActivity) {
            it.getContentIfNotHandled()?.showSnackbar(binding.root)
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        binding.tvAddOrRemoveFavorite.setCompoundDrawablesWithIntrinsicBounds(
            if (isFavorite) R.drawable.ic_favorite_active else R.drawable.ic_favorite,
            0, 0, 0
        )
    }

    private fun setFavoriteIconOnClickListener(isFavorite: Boolean, data: Any) {
        binding.tvAddOrRemoveFavorite.setOnClickListener {
            favoriteViewModel.apply {
                if (isMovieType()) setFavoriteMovie(data as MovieDetail, !isFavorite, this@DetailActivity)
                else setFavoriteTv(data as TvDetail, !isFavorite, this@DetailActivity)
            }
            setFavoriteIcon(!isFavorite)
        }
    }

    private fun isMovieType(): Boolean {
        return when (intent.getStringExtra(EXTRA_MEDIA_TYPE)) {
            EXTRA_MEDIA_MOVIE -> true
            EXTRA_MEDIA_TV -> false
            else -> true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}