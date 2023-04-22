package com.michaldrabik.muzeipixelartextension

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

  companion object {
    const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.michaldrabik.muzeipixelartextension"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)

    val textAuthor = findViewById<TextView>(R.id.textAuthor)
    val textRate = findViewById<TextView>(R.id.textRate)

    textAuthor.text = getString(R.string.text_settings_author, BuildConfig.VERSION_NAME)
    textRate.setOnClickListener {
      startActivity(
        Intent(ACTION_VIEW).apply {
          data = Uri.parse(PLAY_STORE_URL)
        })
    }
  }
}
