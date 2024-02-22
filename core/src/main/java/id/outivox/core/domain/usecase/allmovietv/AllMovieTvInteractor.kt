package id.outivox.core.domain.usecase.allmovietv

import id.outivox.core.domain.repository.allmovietv.AllMovieTvRepository

class AllMovieTvInteractor(private val allMovieTvRepository: AllMovieTvRepository) : AllMovieTvUseCase {
    override fun getMovies(category: String, region: String) = allMovieTvRepository.getMovies(category, region)

    override fun getTvShow(category: String) = allMovieTvRepository.getTvShow(category)
}