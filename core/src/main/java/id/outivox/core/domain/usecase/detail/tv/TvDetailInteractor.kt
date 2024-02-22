package id.outivox.core.domain.usecase.detail.tv

import id.outivox.core.domain.model.detail.*
import id.outivox.core.domain.model.tv.Tv
import id.outivox.core.domain.repository.detail.tv.TvDetailRepository

class TvDetailInteractor(private val tvDetailRepository: TvDetailRepository): TvDetailUseCase {
    override fun getTvDetail(id: String) = tvDetailRepository.getTvDetail(id)
    override fun getTvReviews(id: String) = tvDetailRepository.getTvReviews(id)
    override fun getTvWallpapers(id: String) = tvDetailRepository.getTvWallpapers(id)
    override fun getTvActors(id: String, region: String) = tvDetailRepository.getTvActors(id, region)
    override fun getTvVideos(id: String) = tvDetailRepository.getTvVideos(id)
    override fun getRecommendationsTv(id: String, region: String) = tvDetailRepository.getRecommendationsTv(id, region)
    override fun getSimilarTv(id: String, region: String) = tvDetailRepository.getSimilarTv(id, region)
}