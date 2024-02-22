package id.outivox.movo.presentation.allmovietv.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_CATEGORY
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_PAGE
import id.outivox.movo.presentation.allmovietv.SeeAllTabFragment

class SeeAllViewPagerAdapter(
    fa: FragmentActivity,
    private val category: String,
    private val pageTotal: Int
) :FragmentStateAdapter(fa){

    override fun getItemCount() = if(pageTotal > 30) 30 else pageTotal

    override fun createFragment(position: Int): Fragment {
        val seeAllTabFragment = SeeAllTabFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_MOVIE_CATEGORY, category)
        bundle.putInt(BUNDLE_MOVIE_PAGE, position + 1)
        seeAllTabFragment.arguments = bundle
        return seeAllTabFragment
    }
}