package id.outivox.core.utils

object Constants {

    // Media Type
    const val MEDIA_TV = "tv"
    const val MEDIA_MOVIE = "movie"

    // Movie Discover Sort By
    const val SORT_POPULARITY = "popularity.desc"
    const val SORT_RELEASE = "release_date.desc"
    const val SORT_REVENUE = "revenue.desc"
    const val SORT_RATING = "vote_average.desc"
    const val SORT_VOTE_COUNT = "vote_count.desc"
    const val SORT_TITLE = "original_title.desc"

    // Movie List By
    const val NOW_PLAYING_MOVIE = "now_playing"
    const val POPULAR_MOVIE = "popular"
    const val TOP_RATED_MOVIE = "top_rated"
    const val UPCOMING_MOVIE = "upcoming"
    const val LATEST_MOVIE = "latest"

    // TV Show List By
    const val AIRING_TODAY_TV = "airing_today"
    const val POPULAR_TV = "popular"
    const val TOP_RATED_TV = "top_rated"
    const val ON_THE_AIR_TV = "on_the_air"

    // Movie Search By
    const val MOVIE = "movie"
    const val TV_SHOW = "tv"
    const val PEOPLE = "person"
    const val ALL = "multi"

    // Country/Region List
    const val INDONESIA = "ID"
    const val UNITED_STATES = "US"
    const val CHINA = "CN"
    const val ARAB = "AE"
    const val MALASYIA = "MY"

    // Intent Key
    const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
    const val EXTRA_TV_ID = "EXTRA_TV_ID"
    const val EXTRA_MEDIA_TYPE = "EXTRA_MEDIA_TYPE"
    const val EXTRA_MEDIA_MOVIE = "EXTRA_MEDIA_MOVIE"
    const val EXTRA_MEDIA_TV = "EXTRA_MEDIA_TV"

    // Bundle Key
    const val BUNDLE_MOVIE_ID = "BUNDLE_MOVIE_ID"
    const val BUNDLE_MOVIE_CATEGORY = "BUNDLE_MOVIE_ID"
    const val BUNDLE_MOVIE_DETAIL = "BUNDLE_MOVIE_DETAIL"
    const val BUNDLE_MOVIE_PAGE = "BUNDLE_MOVIE_PAGE"
    const val BUNDLE_MEDIA_TYPE = "BUNDLE_MEDIA_TYPE"
    const val BUNDLE_MEDIA_MOVIE = "BUNDLE_MEDIA_MOVIE"
    const val BUNDLE_MEDIA_TV = "BUNDLE_MEDIA_TV"
    const val BUNDLE_SEARCH_QUERY = "BUNDLE_SEARCH_QUERY"
}