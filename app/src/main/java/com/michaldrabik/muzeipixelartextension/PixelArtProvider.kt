package com.michaldrabik.muzeipixelartextension

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.RemoteActionCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.work.WorkManager
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

  /* kept for backward compatibility with Muzei 3.3 */
  @Suppress("OverridingDeprecatedMember", "DEPRECATION")
  override fun getCommands(artwork: Artwork) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> emptyList()
    else -> listOf(
      com.google.android.apps.muzei.api.UserCommand(COMMAND_DOWNLOAD_ID,
        context?.getString(R.string.text_download))
    )
  }

  /* kept for backward compatibility with Muzei 3.3 */
  @Suppress("OverridingDeprecatedMember")
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

  /* Used for Muzei 3.4+ */
  override fun getCommandActions(artwork: Artwork): List<RemoteActionCompat> {
    val context = context ?: return super.getCommandActions(artwork)
    val intent = Intent(Intent.ACTION_VIEW, artwork.persistentUri)
    return listOf(
      RemoteActionCompat(
        IconCompat.createWithResource(context, R.drawable.muzei_launch_command),
        context.getString(R.string.text_download),
        context.getString(R.string.text_download),
        PendingIntent.getActivity(context, 0, intent, 0)
      ).apply {
        setShouldShowIcon(false)
      }
    )
  }
}