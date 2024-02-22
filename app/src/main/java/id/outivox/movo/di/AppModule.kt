package id.outivox.movo.di

import id.outivox.core.domain.usecase.allmovietv.AllMovieTvInteractor
import id.outivox.core.domain.usecase.allmovietv.AllMovieTvUseCase
import id.outivox.core.domain.usecase.detail.movie.MovieDetailInteractor
import id.outivox.core.domain.usecase.detail.movie.MovieDetailUseCase
import id.outivox.core.domain.usecase.detail.tv.TvDetailInteractor
import id.outivox.core.domain.usecase.detail.tv.TvDetailUseCase
import id.outivox.core.domain.usecase.favorite.FavoriteInteractor
import id.outivox.core.domain.usecase.favorite.FavoriteUseCase
import id.outivox.core.domain.usecase.home.HomeInteractor
import id.outivox.core.domain.usecase.home.HomeUseCase
import id.outivox.core.domain.usecase.search.SearchInteractor
import id.outivox.core.domain.usecase.search.SearchUseCase
import id.outivox.movo.presentation.allmovietv.AllMovieTvViewModel
import id.outivox.movo.presentation.detail.DetailViewModel
import id.outivox.movo.presentation.favorite.FavoriteViewModel
import id.outivox.movo.presentation.home.HomeViewModel
import id.outivox.movo.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    single<HomeUseCase> { HomeInteractor(get()) }
    single<AllMovieTvUseCase> { AllMovieTvInteractor(get()) }
    single<MovieDetailUseCase> { MovieDetailInteractor(get()) }
    single<TvDetailUseCase> { TvDetailInteractor(get()) }
    single<SearchUseCase> { SearchInteractor(get()) }
    single<FavoriteUseCase> { FavoriteInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { AllMovieTvViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}