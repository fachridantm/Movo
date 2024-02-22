package id.outivox.movo.presentation.detail.fragments.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.outivox.core.domain.model.detail.MovieDetail
import id.outivox.core.domain.model.detail.TvDetail
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_MOVIE
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TV
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_DETAIL
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_ID
import id.outivox.movo.presentation.detail.fragments.OtherFragment
import id.outivox.movo.presentation.detail.fragments.OverviewFragment

class DetailViewPagerAdapter<T>(fa: FragmentActivity, val id: String, private val detailData: T) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {

        val otherFragment = OtherFragment()
        val overviewFragment = OverviewFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_MOVIE_ID, id)
        if (detailData is MovieDetail) {
            bundle.putParcelable(BUNDLE_MOVIE_DETAIL, detailData as MovieDetail)
            bundle.putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_MOVIE)
        } else if (detailData is TvDetail) {
            bundle.putParcelable(BUNDLE_MOVIE_DETAIL, detailData as TvDetail)
            bundle.putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_TV)
        }
        otherFragment.arguments = bundle
        overviewFragment.arguments = bundle

        return when (position) {
            0 -> overviewFragment
            else -> otherFragment
        }
    }

}