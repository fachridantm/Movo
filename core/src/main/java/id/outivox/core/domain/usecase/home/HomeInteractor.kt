package id.outivox.core.domain.usecase.home

import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.repository.home.HomeRepository
import io.reactivex.rxjava3.core.Flowable

class HomeInteractor(
    private val homeRepository: HomeRepository
): HomeUseCase {
    override fun getMovies(category: String, page: String): Flowable<Resource<MovieResult>> {
        return homeRepository.getMovies(category, page)
    }

    override fun getTvShow(category: String, page: String): Flowable<Resource<TvResult>> {
        return homeRepository.getTvShow(category, page)
    }

    override fun saveRegion(region: String) {
        homeRepository.saveRegion(region)
    }
}