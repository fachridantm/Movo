package id.outivox.movo.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import id.outivox.core.utils.Constants.BUNDLE_MEDIA_TYPE
import id.outivox.core.utils.Constants.BUNDLE_MOVIE_CATEGORY
import id.outivox.movo.presentation.home.fragment.HomeTabFragment

object HomeTabFragmentGenerator {
    fun generate(category: String, mediaType: String): Fragment {
        val bundle = Bundle()
        val fragment = HomeTabFragment()
        bundle.putString(BUNDLE_MOVIE_CATEGORY, category)
        bundle.putString(BUNDLE_MEDIA_TYPE, mediaType)
        fragment.arguments = bundle
        return fragment
    }
}