package com.michaldrabik.muzeipixelartextension

import android.content.Intent
import android.os.Build
import androidx.work.WorkManager
import com.google.android.apps.muzei.api.UserCommand
import com.google.android.apps.muzei.api.provider.Artwork
import com.google.android.apps.muzei.api.provider.MuzeiArtProvider

class PixelArtProvider : MuzeiArtProvider() {

  companion object {
    private const val COMMAND_DOWNLOAD_ID = 1
  }

  private val workManager by lazy { WorkManager.getInstance() }

  override fun onLoadRequested(initial: Boolean) {
    workManager.enqueue(PixelArtWorker.createRequest())
  }

  /**
   * Not working in Android 10 as of now:
   * https://github.com/romannurik/muzei/issues/644
   **/
  override fun getCommands(artwork: Artwork) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> emptyList()
    else -> listOf(
      UserCommand(COMMAND_DOWNLOAD_ID, context?.getString(R.string.text_download))
    )
  }

  override fun onCommand(artwork: Artwork, id: Int) {
    when (id) {
      COMMAND_DOWNLOAD_ID -> {
        Intent(Intent.ACTION_VIEW).run {
          addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
          data = artwork.persistentUri
          context?.startActivity(this)
        }
      }
    }
  }
}