package com.prashanth.starwars.dependencyInjection

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.prashanth.starwars.BuildConfig
import com.prashanth.starwars.network.StarWarsAPI
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkDaggerModule(private val url: String) {

    private val timeout = 5

    @Provides
    @Singleton
    internal fun provideHttpCache(application: Application): Cache {
        val cacheSize = 30 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(cache: Cache): OkHttpClient {
        val debugInterceptor = HttpLoggingInterceptor()
        debugInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.followRedirects(true)
        client.followSslRedirects(true)
        client.retryOnConnectionFailure(true)
        client.connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
        client.readTimeout(timeout.toLong(), TimeUnit.SECONDS)
        client.writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            client.addInterceptor(debugInterceptor)
        }
        return client.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideStarWarsAPI(retrofit: Retrofit): StarWarsAPI {
        return retrofit.create(StarWarsAPI::class.java)
    }

}