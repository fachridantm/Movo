package id.outivox.movo.presentation.home.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.outivox.core.utils.Constants
import id.outivox.core.utils.Constants.AIRING_TODAY_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.POPULAR_TV
import id.outivox.core.utils.Constants.TOP_RATED_MOVIE
import id.outivox.core.utils.Constants.TOP_RATED_TV
import id.outivox.movo.utils.HomeTabFragmentGenerator

class HomeViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {

        val topRatedMovie = HomeTabFragmentGenerator.generate(TOP_RATED_MOVIE, BUNDLE_MEDIA_MOVIE)
        val topRatedTv = HomeTabFragmentGenerator.generate(TOP_RATED_TV, BUNDLE_MEDIA_TV)
        val airingTodayTv = HomeTabFragmentGenerator.generate(AIRING_TODAY_TV, BUNDLE_MEDIA_TV)
        val popularTv = HomeTabFragmentGenerator.generate(POPULAR_TV, BUNDLE_MEDIA_TV)

        return listOf(airingTodayTv, topRatedMovie, topRatedTv, popularTv)[position]
    }

}