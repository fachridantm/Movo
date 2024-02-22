package id.outivox.core.domain.usecase.search

import id.outivox.core.domain.repository.search.SearchRepository

class SearchInteractor(private val searchRepository: SearchRepository) : SearchUseCase {
    override fun searchMovie(query: String, region: String) = searchRepository.searchMovie(query, region)
    override fun searchTvShow(query: String) = searchRepository.searchTvShow(query)
}