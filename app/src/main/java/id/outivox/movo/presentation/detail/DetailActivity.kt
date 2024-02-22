package id.outivox.movo.presentation.detail

import android.os.Bundle
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
import id.outivox.movo.R
import id.outivox.movo.databinding.ActivityDetailBinding
import id.outivox.movo.presentation.detail.fragments.adapter.DetailViewPagerAdapter
import id.outivox.movo.utils.HelperFunction
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding as ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModel()

    private var _movieDetail: Resource<MovieDetail>? = null
    private val movieDetail get() = _movieDetail as Resource<MovieDetail>

    private var _tvDetail: Resource<TvDetail>? = null
    private val tvDetail get() = _tvDetail as Resource<TvDetail>

    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        HelperFunction.transparentStatusbar(this)

        id = intent.getIntExtra(EXTRA_MOVIE_ID, 0).toString()

        initView()
        initObserver()
    }

    private fun initView() {
        binding.toolbarDetail.apply {
            setNavigationIcon(R.drawable.ic_back)
            setSupportActionBar(this)
        }
    }

    private fun setUpTabBar() {
        if (isMovieType()) {
            if (movieDetail.data == null) return
            binding.vpOverviewAndOther.apply {
                adapter = movieDetail.data?.let { DetailViewPagerAdapter(this@DetailActivity, this@DetailActivity.id, it) }
                isUserInputEnabled = false
            }
        } else {
            if (tvDetail.data == null) return
            binding.vpOverviewAndOther.apply {
                adapter = tvDetail.data?.let { DetailViewPagerAdapter(this@DetailActivity, this@DetailActivity.id, it) }
                isUserInputEnabled = false
            }
        }

        TabLayoutMediator(binding.tabDetail, binding.vpOverviewAndOther) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.overview)
                1 -> tab.text = getString(R.string.others)
            }
        }.attach()
    }

    private fun setUpMovieDetail() {
        binding.apply {
            with(movieDetail.data) {
                if (this == null) return

                imgPoster.loadImageOnly(posterPath)
                tvTitle.text = title
                collapsingToolbar.title = title
                tvRatingCount.text = voteAverage.toString().take(3)
                tvRatersCount.text = getString(R.string.vote_count, voteCount)
                tvReleaseData.text = HelperFunction.dateFormatter(releaseDate)
                tvDurationOrEpisode.text = HelperFunction.durationFormatter(duration)
            }
        }
    }

    private fun setUpTvDetail() {
        binding.apply {
            with(tvDetail.data) {
                if (this == null) return

                imgPoster.loadImageOnly(posterPath)
                tvTitle.text = title
                collapsingToolbar.title = title
                tvRatingCount.text = voteAverage.toString().take(3)
                tvRatersCount.text = getString(R.string.vote_count, voteCount)
                tvReleaseData.text = HelperFunction.dateFormatter(firstAirDate)
                tvDurationOrEpisode.text = getString(R.string.eps_and_seasons, numberOfEpisodes, numberOfSeasons)
            }
        }
    }

    private fun initObserver() {
        if (isMovieType()) {
            viewModel.getMovieDetail(id)
            viewModel.movieDetailResponse.observe(this) {
                _movieDetail = it
                setUpMovieDetail()
                setUpTabBar()
            }
        } else {
            viewModel.getTvDetail(id)
            viewModel.tvDetailResponse.observe(this) {
                _tvDetail = it
                setUpTvDetail()
                setUpTabBar()
            }
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