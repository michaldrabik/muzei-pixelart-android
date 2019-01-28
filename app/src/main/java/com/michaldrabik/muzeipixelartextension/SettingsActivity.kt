package com.michaldrabik.muzeipixelartextension

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

  companion object {
    const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.michaldrabik.muzeipixelartextension"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    textAuthor.text = getString(R.string.text_settings_author, BuildConfig.VERSION_NAME)
    textRate.setOnClickListener {
      startActivity(
        Intent(ACTION_VIEW).apply {
          data = Uri.parse(PLAY_STORE_URL)
        })
    }
  }
}
