package id.outivox.core.di

import id.outivox.core.data.local.LocalDataSource
import id.outivox.core.data.remote.RemoteDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { RemoteDataSource(get()) }
    factory { LocalDataSource(get(), get()) }
}