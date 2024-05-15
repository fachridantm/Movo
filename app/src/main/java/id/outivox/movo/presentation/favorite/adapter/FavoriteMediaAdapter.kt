package id.outivox.movo.presentation.favorite.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.movo.presentation.favorite.FavoriteMediaFragment

class FavoriteMediaAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val movieTabFragment = FavoriteMediaFragment()
        val movieBundle = Bundle()
        movieBundle.putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_MOVIE)
        movieTabFragment.arguments = movieBundle

        val tvTabFragment = FavoriteMediaFragment()
        val tvBundle = Bundle()
        tvBundle.putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_TV)
        tvTabFragment.arguments = tvBundle

        return if (position == 0) movieTabFragment else tvTabFragment
    }
}