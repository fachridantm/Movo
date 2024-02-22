package id.outivox.core.data.repository.home

import id.outivox.core.data.NetworkResource
import id.outivox.core.data.local.LocalDataSource
import id.outivox.core.data.remote.RemoteDataSource
import id.outivox.core.data.remote.source.response.movie.MovieResponse
import id.outivox.core.data.remote.source.response.tv.TvResponse
import id.outivox.core.domain.model.Resource
import id.outivox.core.domain.model.movie.MovieResult
import id.outivox.core.domain.model.tv.TvResult
import id.outivox.core.domain.repository.home.HomeRepository
import id.outivox.core.mapper.HomeMapper.map
import io.reactivex.rxjava3.core.Flowable

class HomeRepositoryImpl(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
): HomeRepository {
    override fun getMovies(category: String, page: String): Flowable<Resource<MovieResult>> {
        return object : NetworkResource<MovieResult, MovieResponse>() {
            override fun createResult(data: MovieResponse): MovieResult {
                return data.map()
            }

            override fun createCall(): Flowable<MovieResponse> {
                return remoteDataSource.getMoviesByCategory(category, page)
            }

        }.asFlowable()
    }

    override fun getTvShow(category: String, page: String): Flowable<Resource<TvResult>> {
        return object : NetworkResource<TvResult, TvResponse>() {
            override fun createResult(data: TvResponse): TvResult {
                return data.map()
            }

            override fun createCall(): Flowable<TvResponse> {
                return remoteDataSource.getTvByCategory(category, page)
            }

        }.asFlowable()
    }

    override fun saveRegion(region: String) {
        TODO("Not yet implemented")
    }
}