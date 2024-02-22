package id.outivox.core.di

import id.outivox.core.data.remote.source.network.ApiService
import id.outivox.movo.core.BuildConfig.BASE_URL
import id.outivox.movo.core.BuildConfig.DEBUG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                return@addInterceptor it.proceed(request)
            }
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                )
            )
            .pingInterval(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}