package id.outivox.core.domain.usecase.home

import id.outivox.core.domain.repository.home.HomeRepository
import kotlinx.coroutines.CoroutineScope

class HomeInteractor(private val homeRepository: HomeRepository) : HomeUseCase {
    override fun getMovies(category: String, region: String, scope: CoroutineScope) = homeRepository.getMovies(category, region, scope)
    override fun getTvShow(category: String) = homeRepository.getTvShow(category)
    override fun saveRegion(region: String) { homeRepository.saveRegion(region) }
}