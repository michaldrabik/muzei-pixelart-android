package com.michaldrabik.muzeipixelartextension.api.retrofit

import com.michaldrabik.muzeipixelartextension.model.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesService {

  @GET("wallpapers/random")
  fun randomImages(@Query("count") count: Int): Call<List<Image>>

}