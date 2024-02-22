package id.outivox.movo.presentation.allmovietv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import id.outivox.core.utils.Constants.AIRING_TODAY_TV
import id.outivox.core.utils.Constants.LATEST_MOVIE
import id.outivox.core.utils.Constants.NOW_PLAYING_MOVIE
import id.outivox.core.utils.Constants.ON_THE_AIR_TV
import id.outivox.core.utils.Constants.POPULAR_MOVIE
import id.outivox.core.utils.Constants.POPULAR_TV
import id.outivox.core.utils.Constants.TOP_RATED_MOVIE
import id.outivox.core.utils.Constants.TOP_RATED_TV
import id.outivox.core.utils.Constants.UPCOMING_MOVIE
import id.outivox.movo.databinding.FragmentAllMovieTvBinding
import id.outivox.movo.presentation.allmovietv.adapter.SeeAllViewPagerAdapter

class AllMovieTvFragment : Fragment() {

    private var _binding: FragmentAllMovieTvBinding? = null
    private val binding get() = _binding as FragmentAllMovieTvBinding

    private lateinit var category: String
    private var totalPages = 1

    val args: AllMovieTvFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMovieTvBinding.inflate(layoutInflater)

        category = args.category
        totalPages = args.totalPages

        setUpPageBar()
        setUpSeeAllTitle()
        setUpView()

        return binding.root
    }

    private fun setUpView() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpSeeAllTitle() {
        binding.tvMovieTvCategory.text = categoryTextFormatter()
    }

    private fun categoryTextFormatter(): String {
        return when (category) {
            POPULAR_MOVIE -> "Popular Movie"
            TOP_RATED_MOVIE -> "Top Rated Movie"
            UPCOMING_MOVIE -> "Upcoming Movie"
            NOW_PLAYING_MOVIE -> "Now Playing Movie"
            LATEST_MOVIE -> "Latest Movie"
            AIRING_TODAY_TV -> "Airing Today TV"
            POPULAR_TV -> "Popular TV"
            TOP_RATED_TV -> "Top Rated TV"
            ON_THE_AIR_TV -> "On The Air TV"
            else -> category
        }
    }

    private fun setUpPageBar() {
        binding.apply {
            binding.vpPage.isUserInputEnabled = false

            vpPage.adapter = activity?.let { SeeAllViewPagerAdapter(it, category, totalPages) }
            TabLayoutMediator(pageTabs, vpPage) { tab, position ->
                tab.text = (position + 1).toString()
            }.attach()

            
        }
    }


}