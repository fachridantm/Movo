package id.outivox.core.di

import id.outivox.movo.di.useCaseModule
import id.outivox.movo.di.viewModelModule

val listModules = listOf(
    databaseModule,
    networkModule,
    repositoryModule,
    dataSourceModule,
    useCaseModule,
    viewModelModule
)