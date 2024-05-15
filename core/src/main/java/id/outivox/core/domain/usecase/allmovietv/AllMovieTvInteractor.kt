package id.outivox.core.domain.usecase.allmovietv

import id.outivox.core.domain.repository.allmovietv.AllMovieTvRepository
import kotlinx.coroutines.CoroutineScope

class AllMovieTvInteractor(private val allMovieTvRepository: AllMovieTvRepository) : AllMovieTvUseCase {
    override fun getMovies(category: String, region: String, scope: CoroutineScope) = allMovieTvRepository.getMovies(category, region, scope)

    override fun getTvShow(category: String) = allMovieTvRepository.getTvShow(category)
}