package id.outivox.movo.presentation.search.fragment.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_SEARCH_QUERY
import id.outivox.movo.presentation.search.fragment.SearchTabFragment

class SearchMediaViewPagerAdapter(
    fa: FragmentActivity,
    private val mediaType: String,
    private val querySearch: String,
    private val pageTotal: Int,
) : FragmentStateAdapter(fa) {

    override fun getItemCount() = if (pageTotal > 30) 30 else pageTotal

    override fun createFragment(position: Int): Fragment {

        val searchTabFragment = SearchTabFragment()
        val bundle = Bundle()
        bundle.putString(BUNDLE_SEARCH_QUERY, querySearch)
        bundle.putString(BUNDLE_MEDIA_TYPE, mediaType)
        bundle.putInt(BUNDLE_SEARCH_PAGE, position + 1)
        searchTabFragment.arguments = bundle

        return searchTabFragment
    }

    companion object {
        const val BUNDLE_SEARCH_PAGE = "BUNDLE_SEARCH_PAGE"
    }

}