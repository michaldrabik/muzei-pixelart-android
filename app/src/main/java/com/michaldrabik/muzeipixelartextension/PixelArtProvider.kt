package com.michaldrabik.muzeipixelartextension

import androidx.work.WorkManager
import com.google.android.apps.muzei.api.provider.MuzeiArtProvider

class PixelArtProvider : MuzeiArtProvider() {

  private val workManager by lazy { WorkManager.getInstance() }

  override fun onLoadRequested(initial: Boolean) {
    workManager.enqueue(PixelArtWorker.createRequest())
  }
}