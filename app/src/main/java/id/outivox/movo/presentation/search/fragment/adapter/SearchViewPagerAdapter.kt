package id.outivox.movo.presentation.search.fragment.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.outivox.core.utils.Constants
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_SEARCH_QUERY
import id.outivox.movo.presentation.search.fragment.SearchMediaFragment

class SearchViewPagerAdapter(fa: FragmentActivity, private val querySearch: String) : FragmentStateAdapter(fa) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val movieTabFragment = SearchMediaFragment()
        val movieBundle = Bundle()
        movieBundle.putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_MOVIE)
        movieBundle.putString(BUNDLE_SEARCH_QUERY, querySearch)
        movieTabFragment.arguments = movieBundle

        val tvTabFragment = SearchMediaFragment()
        val tvBundle = Bundle()
        tvBundle.putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_TV)
        tvBundle.putString(BUNDLE_SEARCH_QUERY, querySearch)
        tvTabFragment.arguments = tvBundle
        return when (position) {
            0 -> movieTabFragment
            else -> tvTabFragment
        }
    }


}