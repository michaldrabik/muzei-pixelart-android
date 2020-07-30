package com.michaldrabik.muzeipixelartextension

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.BackoffPolicy.LINEAR
import androidx.work.Constraints
import androidx.work.NetworkType.CONNECTED
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.apps.muzei.api.provider.Artwork
import com.google.android.apps.muzei.api.provider.ProviderContract
import com.michaldrabik.muzeipixelartextension.api.Api
import com.michaldrabik.muzeipixelartextension.api.retrofit.RetrofitFactory
import java.util.concurrent.TimeUnit

class PixelArtWorker(
  context: Context,
  workerParams: WorkerParameters
) : Worker(context, workerParams) {

  companion object {
    private const val TAG = "PixelArtWorker"
    private const val IMAGES_COUNT = 1

    fun createRequest() =
      OneTimeWorkRequest.Builder(PixelArtWorker::class.java)
        .setConstraints(
          Constraints.Builder()
            .setRequiredNetworkType(CONNECTED)
            .build()
        )
        .setBackoffCriteria(LINEAR, 3, TimeUnit.MINUTES)
        .build()
  }

  private val api by lazy { Api(RetrofitFactory.create()) }

  override fun doWork() = try {
    val images = api.imagesService.randomImages(IMAGES_COUNT).execute().body()!!

    if (images.isEmpty()) {
      Log.e(TAG, "No images returned from API")
      Result.failure()
    }

    val providerClient = ProviderContract.getProviderClient(
      applicationContext,
      BuildConfig.PIXELART_AUTHORITY
    )
    val attributionString = applicationContext.getString(R.string.attribution)
    providerClient.addArtwork(images.map { image ->
      Artwork(
        token = image.id,
        title = attributionString,
        attribution = attributionString,
        persistentUri = Uri.parse(image.url),
        webUri = Uri.EMPTY
      )
    })

    Result.success()
  } catch (e: Exception) {
    Log.e(TAG, "Error reading API response", e)
    Result.retry()
  }
}