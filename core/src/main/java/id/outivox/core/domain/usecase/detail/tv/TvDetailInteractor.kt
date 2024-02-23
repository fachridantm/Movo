package id.outivox.core.domain.usecase.detail.tv

import id.outivox.core.domain.model.detail.*
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.repository.detail.tv.TvDetailRepository

class TvDetailInteractor(private val tvDetailRepository: TvDetailRepository): TvDetailUseCase {
    override fun getTvDetail(id: Int) = tvDetailRepository.getTvDetail(id)
    override fun getSimilarTv(id: Int) = tvDetailRepository.getSimilarTv(id)
    override fun getTvReviews(id: Int) = tvDetailRepository.getTvReviews(id)
    override fun getRecommendationsTv(id: Int) = tvDetailRepository.getRecommendationsTv(id)
    override fun getTvWallpapers(id: Int) = tvDetailRepository.getTvWallpapers(id)
    override fun getTvActors(id: Int) = tvDetailRepository.getTvActors(id)
    override fun getTvVideos(id: Int) = tvDetailRepository.getTvVideos(id)
}
