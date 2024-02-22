package id.outivox.movo.utils

import id.outivox.movo.R
import id.outivox.movo.databinding.ItemVerticalMovieBinding

fun ItemVerticalMovieBinding.setUpRatingStars(originRating: Double) {
    val rating = originRating / 2
    if (rating >= 0.5) {
        stars1.setImageResource(R.drawable.ic_half_stars)
        if (rating >= 1.0) {
            stars1.setImageResource(R.drawable.ic_stars)
            if (rating >= 1.5) {
                stars2.setImageResource(R.drawable.ic_half_stars)
                if (rating >= 2.0) {
                    stars2.setImageResource(R.drawable.ic_stars)
                    if (rating >= 2.5) {
                        stars3.setImageResource(R.drawable.ic_half_stars)
                        if (rating >= 3.0) {
                            stars3.setImageResource(R.drawable.ic_stars)
                            if (rating >= 3.5) {
                                stars4.setImageResource(R.drawable.ic_half_stars)
                                if (rating >= 4.0) {
                                    stars4.setImageResource(R.drawable.ic_stars)
                                    if (rating >= 4.5) {
                                        stars5.setImageResource(R.drawable.ic_half_stars)
                                        if (rating == 5.0) {
                                            stars5.setImageResource(R.drawable.ic_stars)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}