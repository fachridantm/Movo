package id.outivox.core.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import id.outivox.core.data.remote.source.network.ApiService
import id.outivox.movo.core.BuildConfig.API_KEY
import id.outivox.movo.core.BuildConfig.BASE_URL
import id.outivox.movo.core.BuildConfig.DEBUG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        ChuckerCollector(get(), true, RetentionManager.Period.ONE_HOUR)
    }

    single {
        ChuckerInterceptor.Builder(get())
            .collector(get<ChuckerCollector>())
            .maxContentLength(250_000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .createShortcut(true)
            .build()
    }

    single {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor { chain ->
                chain.request()
                    .newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer $API_KEY")
                    .build()
                    .let(chain::proceed)
            }
            .apply {
                if (DEBUG) {
                    addInterceptor(get<ChuckerInterceptor>())
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                } else {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE))
                }
            }
            .pingInterval(10, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}