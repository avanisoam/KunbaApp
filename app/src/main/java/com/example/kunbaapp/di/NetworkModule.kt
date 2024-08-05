package com.example.kunbaapp.di

import com.example.kunbaapp.data.network.KunbaAppApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//private const val BASE_URL = "http://192.168.0.232:5233/api/MobileApi/"
private const val BASE_URL = "http://192.168.0.232:5233/api/MobileApiV2/"
private fun provideHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()



private fun provideRetrofit(
    baseUrl : String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

private fun provideService(retrofit: Retrofit): KunbaAppApiService =
    retrofit.create(KunbaAppApiService::class.java)

val networkModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(BASE_URL,get(),get()) }
    single { provideService(get()) }
}