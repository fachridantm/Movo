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

class DetailAdapter<T>(
    fa: FragmentActivity,
    private val id: Int,
    private val data: T
) : FragmentStateAdapter(fa) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val otherFragment = OtherFragment()
        val overviewFragment = OverviewFragment()

        val bundle = Bundle()
        bundle.apply {
            putInt(BUNDLE_MOVIE_ID, id)
            if (data is MovieDetail) {
                putParcelable(BUNDLE_MOVIE_DETAIL, data as MovieDetail)
                putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_MOVIE)
            } else if (data is TvDetail) {
                putParcelable(BUNDLE_MOVIE_DETAIL, data as TvDetail)
                putString(BUNDLE_MEDIA_TYPE, BUNDLE_MEDIA_TV)
            }
            otherFragment.arguments = this@apply
            overviewFragment.arguments = this@apply
        }

        return if (position == 0) overviewFragment else otherFragment
    }

}