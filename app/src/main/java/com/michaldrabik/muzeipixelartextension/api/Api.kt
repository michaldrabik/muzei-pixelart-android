package com.michaldrabik.muzeipixelartextension.api

import com.michaldrabik.muzeipixelartextension.api.retrofit.ImagesService
import retrofit2.Retrofit

class Api(retrofit: Retrofit) {

  val imagesService: ImagesService by lazy { retrofit.create(ImagesService::class.java) }

}