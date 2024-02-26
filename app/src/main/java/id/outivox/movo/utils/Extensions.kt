package id.outivox.movo.utils

import id.outivox.movo.R
import id.outivox.movo.databinding.ItemReviewDetailBinding
import id.outivox.movo.databinding.ItemVerticalMovieBinding

fun ItemVerticalMovieBinding.setupRatingStars(originRating: Double) {
    val rating = originRating / 2
    if (rating >= 0.5) {
        ivStars1.setImageResource(R.drawable.ic_half_stars)
        if (rating >= 1.0) {
            ivStars1.setImageResource(R.drawable.ic_stars)
            if (rating >= 1.5) {
                ivStars2.setImageResource(R.drawable.ic_half_stars)
                if (rating >= 2.0) {
                    ivStars2.setImageResource(R.drawable.ic_stars)
                    if (rating >= 2.5) {
                        ivStars3.setImageResource(R.drawable.ic_half_stars)
                        if (rating >= 3.0) {
                            ivStars3.setImageResource(R.drawable.ic_stars)
                            if (rating >= 3.5) {
                                ivStars4.setImageResource(R.drawable.ic_half_stars)
                                if (rating >= 4.0) {
                                    ivStars4.setImageResource(R.drawable.ic_stars)
                                    if (rating >= 4.5) {
                                        ivStars5.setImageResource(R.drawable.ic_half_stars)
                                        if (rating == 5.0) {
                                            ivStars5.setImageResource(R.drawable.ic_stars)
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

fun ItemReviewDetailBinding.setupRatingStars(originRating: Double) {
    val rating = originRating / 2
    if (rating >= 0.5) {
        ivStars1.setImageResource(R.drawable.ic_half_stars)
        if (rating >= 1.0) {
            ivStars1.setImageResource(R.drawable.ic_stars)
            if (rating >= 1.5) {
                ivStars2.setImageResource(R.drawable.ic_half_stars)
                if (rating >= 2.0) {
                    ivStars2.setImageResource(R.drawable.ic_stars)
                    if (rating >= 2.5) {
                        ivStars3.setImageResource(R.drawable.ic_half_stars)
                        if (rating >= 3.0) {
                            ivStars3.setImageResource(R.drawable.ic_stars)
                            if (rating >= 3.5) {
                                ivStars4.setImageResource(R.drawable.ic_half_stars)
                                if (rating >= 4.0) {
                                    ivStars4.setImageResource(R.drawable.ic_stars)
                                    if (rating >= 4.5) {
                                        ivStars5.setImageResource(R.drawable.ic_half_stars)
                                        if (rating == 5.0) {
                                            ivStars5.setImageResource(R.drawable.ic_stars)
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