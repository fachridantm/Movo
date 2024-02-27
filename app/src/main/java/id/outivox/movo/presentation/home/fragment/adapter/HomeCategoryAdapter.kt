package id.outivox.movo.presentation.home.fragment.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.outivox.core.utils.Constants.AIRING_TODAY_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_CATEGORY
import id.outivox.core.utils.Constants.POPULAR_TV
import id.outivox.core.utils.Constants.TOP_RATED_MOVIE
import id.outivox.core.utils.Constants.TOP_RATED_TV
import id.outivox.movo.presentation.home.fragment.HomeTabFragment

class HomeCategoryAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {

        val topRatedMovie = generateData(TOP_RATED_MOVIE, BUNDLE_MEDIA_MOVIE)
        val topRatedTv = generateData(TOP_RATED_TV, BUNDLE_MEDIA_TV)
        val airingTodayTv = generateData(AIRING_TODAY_TV, BUNDLE_MEDIA_TV)
        val popularTv = generateData(POPULAR_TV, BUNDLE_MEDIA_TV)

        return listOf(airingTodayTv, topRatedMovie, topRatedTv, popularTv)[position]
    }

    private fun generateData(category: String, mediaType: String): Fragment {
        val bundle = Bundle()
        val fragment = HomeTabFragment()
        bundle.putString(BUNDLE_MOVIE_CATEGORY, category)
        bundle.putString(BUNDLE_MEDIA_TYPE, mediaType)
        fragment.arguments = bundle
        return fragment
    }
}