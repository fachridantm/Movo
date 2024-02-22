package id.outivox.core.di

import id.outivox.core.data.repository.allmovie.AllMovieTvRepositoryImpl
import id.outivox.core.data.repository.detail.movie.MovieDetailRepositoryImpl
import id.outivox.core.data.repository.detail.tv.TvDetailRepositoryImpl
import id.outivox.core.data.repository.home.HomeRepositoryImpl
import id.outivox.core.data.repository.search.SearchRepositoryImpl
import id.outivox.core.domain.repository.allmovietv.AllMovieTvRepository
import id.outivox.core.domain.repository.detail.movie.MovieDetailRepository
import id.outivox.core.domain.repository.detail.tv.TvDetailRepository
import id.outivox.core.domain.repository.home.HomeRepository
import id.outivox.core.domain.repository.search.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get(), get()) }
    single<MovieDetailRepository> { MovieDetailRepositoryImpl(get(), get()) }
    single<TvDetailRepository> { TvDetailRepositoryImpl(get(), get()) }
    single<AllMovieTvRepository> { AllMovieTvRepositoryImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}