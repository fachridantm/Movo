package id.outivox.movo.di

import id.outivox.core.di.dataSourceModule
import id.outivox.core.di.databaseModule
import id.outivox.core.di.networkModule
import id.outivox.core.di.repositoryModule

val listModules = listOf(
    databaseModule,
    networkModule,
    repositoryModule,
    dataSourceModule,
    useCaseModule,
    viewModelModule
)