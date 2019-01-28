package com.michaldrabik.muzeipixelartextension.api.retrofit

import com.michaldrabik.muzeipixelartextension.API_KEY
import com.michaldrabik.muzeipixelartextension.STAGE
import com.michaldrabik.muzeipixelartextension.api.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {

  private const val BASE_API_URL = "https://1dvmpfw9xi.execute-api.eu-central-1.amazonaws.com"

  fun create(): Retrofit {
    val httpClient = OkHttpClient.Builder()
      .addInterceptor(AuthInterceptor(API_KEY))
      .addInterceptor(HttpLoggingInterceptor().apply {
        level = Level.BODY
      })
      .build()

    return Retrofit.Builder()
      .baseUrl("$BASE_API_URL/$STAGE/")
      .client(httpClient)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()
  }
}