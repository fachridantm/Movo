package id.outivox.core.data.repository.search

import id.outivox.core.data.NetworkResource
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.response.movie.MovieResponse
import id.outivox.core.data.remote.source.response.tv.TvResponse
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.repository.search.SearchRepository
import id.outivox.core.mapper.HomeMapper.map
import io.reactivex.rxjava3.core.Flowable

class SearchRepositoryImpl(
    val remoteDataSource: RemoteDataSource
): SearchRepository {
    override fun searchMovie(query: String, page: String): Flowable<Resource<MovieResult>> {
        return object: NetworkResource<MovieResult, MovieResponse>(){
            override fun createResult(data: MovieResponse): MovieResult {
                return data.map()
            }

            override fun createCall(): Flowable<MovieResponse> {
                return remoteDataSource.searchMovieByQuery(query, page)
            }

        }.asFlowable()
    }

    override fun searchTvShow(query: String, page: String): Flowable<Resource<TvResult>> {
        return object: NetworkResource<TvResult, TvResponse>() {
            override fun createResult(data: TvResponse): TvResult {
                return data.map()
            }

            override fun createCall(): Flowable<TvResponse> {
                return remoteDataSource.searchTvByQuery(query, page)
            }

        }.asFlowable()
    }

}